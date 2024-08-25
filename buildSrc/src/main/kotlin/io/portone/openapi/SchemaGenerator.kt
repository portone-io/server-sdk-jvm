package io.portone.openapi

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.Closeable
import java.math.BigInteger
import java.nio.file.Path
import java.time.Instant
import java.util.concurrent.CompletableFuture

private enum class ExportMode {
    NORMAL,
    ERROR_UNION,
    ERROR_CASE,
}

interface Documented {
    val title: String?
    val description: String?

    val joinSeparateParagraph: String?
        get() = listOfNotNull(title, description)
            .distinct()
            .joinToString("\n\n")
            .let { if (it.isEmpty()) null else it }

    val joinSingleLine: String?
        get() = listOfNotNull(title, description)
            .distinct()
            .joinToString(" - ")
            .let { if (it.isEmpty()) null else it }
}

private data class Schema(val spec: Spec, var isPublic: Boolean, val exportMode: ExportMode)

sealed interface Spec : Documented {
    val name: String
    val asType: TypeName

    val asOptional: Spec
    fun withTitle(title: String?): Spec
    fun withDescription(description: String?): Spec
}

private data class MinimalSpec(
    override val name: String,
    override val asType: TypeName,
    override val title: String?,
    override val description: String?,
) : Spec {
    override val asOptional: Spec
        get() = copy(asType = asType.copy(nullable = true))
    override fun withTitle(title: String?): Spec =
        copy(title = title)
    override fun withDescription(description: String?): Spec =
        copy(description = description)
}

private data class EnumValue(
    val name: String,
    override val title: String?,
    override val description: String?,
) : Documented

private data class EnumSpec(
    override val name: String,
    override val asType: TypeName,
    val values: List<EnumValue>,
    override val title: String?,
    override val description: String?,
) : Spec {
    override val asOptional: Spec
        get() = copy(asType = asType.copy(nullable = true))
    override fun withTitle(title: String?): Spec =
        copy(title = title)
    override fun withDescription(description: String?): Spec =
        copy(description = description)
}

data class ObjectSpec(
    override val name: String,
    override val asType: TypeName,
    val properties: MutableList<Spec> = mutableListOf(),
    val discriminator: Discriminator? = null,
    val parents: MutableList<String> = mutableListOf(),
    override val title: String?,
    override val description: String?,
) : Spec {
    override val asOptional: Spec
        get() = copy(asType = asType.copy(nullable = true))
    override fun withTitle(title: String?): Spec =
        copy(title = title)
    override fun withDescription(description: String?): Spec =
        copy(description = description)
}

data class Discriminator(
    val propertyName: String,
    val mapping: List<Pair<String, String>>,
)

data class Operation(
    val name: String,
    val path: String,
    val method: String,
    val pathParams: List<Spec>,
    val queryParams: List<Spec>,
    val bodyParams: List<Spec>,
    val body: TypeName?,
    val success: TypeName,
    val returns: String?,
    val error: TypeName,
    val errorCases: List<String>,
    override val title: String?,
    override val description: String?,
) : Documented

private fun parseRef(ref: String): String =
    ref.substringAfterLast('/')

class SchemaGenerator(private val document: JsonObject, private val includePrefixes: List<String>, private val targetDirectory: Path) {
    private val resolvedNames: MutableSet<String> = mutableSetOf()
    private val operations: MutableList<Operation> = mutableListOf()
    private val schemas: MutableMap<String, Schema> = mutableMapOf()

    private val documentSchemas: JsonObject?
        get() = document.getObject("components")?.getObject("schemas")

    private fun visitDocument() {
        val paths = document.getObject("paths") ?: return

        for ((path, pathItem) in paths.entries) {
            if (includePrefixes.none { path.startsWith(it) } || pathItem !is JsonObject || pathItem.isEmpty()) continue

            for ((method, schema) in pathItem.entries) {
                if (schema !is JsonObject) continue
                val operationId = schema.getString("operationId") ?: continue
                val title = schema.getString("x-portone-title") ?: continue
                val description = schema.getString("x-portone-description") ?: continue

                val portoneError = schema.getObject("x-portone-error") ?: continue
                val errorRef = portoneError.getString("\$ref") ?: continue
                val error = exportType(parseRef(errorRef), ExportMode.ERROR_UNION, publicUse = false)
                val errorCases = (schemas[parseRef(errorRef)]!!.spec as ObjectSpec).discriminator!!.mapping.map { it.second }

                val (body, bodyParams) = schema.getObject("requestBody")
                    ?.getObject("content")
                    ?.getObject("application/json")
                    ?.getObject("schema")?.jsonObject
                    ?.getString("\$ref")?.let { bodyRef ->
                        val body = exportType(parseRef(bodyRef), skipEmpty = true, publicUse = false)
                        val bodyParams = (schemas[parseRef(bodyRef)]!!.spec as ObjectSpec).properties.toList()
                        Pair(body, bodyParams)
                    } ?: Pair(null, listOf())

                val parametersSchema = schema["parameters"]?.jsonArray
                val pathParams = mutableListOf<Spec>()
                val queryParams = mutableListOf<Spec>()
                if (parametersSchema != null) {
                    for (parameter in parametersSchema) {
                        parameter as JsonObject
                        if (parameter.containsKey("\$ref")) throw RuntimeException()
                        val name = parameter.getString("name") ?: continue
                        if (name == "requestBody") continue
                        val `in` = parameter.getString("in") ?: continue
                        val parameterSchema = parameter.getObject("schema") ?: continue
                        val parameterDescription = parameter.getString("description")
                        val spec = visitSchema(parameterSchema, name, publicUse = true).withDescription(parameterDescription).let {
                            if (parameter.getBoolean("required") != true) it.asOptional
                            else it
                        }
                        if (`in` == "path") {
                            pathParams.add(spec)
                        } else if (`in` == "query") {
                            queryParams.add(spec)
                        }
                    }
                }

                val response = schema.getObject("responses")!!
                val success = response.getObject("200")!!.getObject("content")!!
                    .getObject("application/json")!!.getObject("schema")!!.getString("\$ref")!!.let { successRef ->
                        exportType(parseRef(successRef), skipEmpty = true)
                    }
                val returns = response.getObject("200")?.getString("description")

                operations.add(Operation(
                    name = operationId,
                    path = path,
                    method = method,
                    pathParams = pathParams,
                    queryParams = queryParams,
                    bodyParams = bodyParams,
                    body = body,
                    success = success,
                    returns = returns,
                    error = error,
                    errorCases = errorCases,
                    title = title,
                    description = description,
                ))
            }
        }
    }

    private fun visitSchema(schema: JsonObject, name: String, exportMode: ExportMode? = null, skipEmpty: Boolean = false, publicUse: Boolean): Spec {
        val title = schema.getString("x-portone-title") ?: schema.getString("title")
        val description = schema.getString("x-portone-description") ?: schema.getString("description")

        val ref = schema.getString("\$ref")
        if (ref != null) {
            val asType = parseRef(ref)
            return MinimalSpec(
                name = name,
                asType = exportType(asType, skipEmpty = skipEmpty),
                title = title,
                description = description,
            )
        }

        val discriminator = schema.getObject("discriminator")
        if (discriminator != null) {
            val propertyName = discriminator.getString("propertyName")
            val mapping = discriminator.getObject("mapping")
            if (propertyName != null && mapping != null) {
                val refs = mutableSetOf<String>()
                val map = mutableListOf<Pair<String, String>>()
                val children = mutableListOf<ObjectSpec>()
                for ((key, childRef) in mapping.entries) {
                    val childName = parseRef(childRef.string)
                    exportType(childName, exportMode = when (exportMode) {
                        ExportMode.NORMAL -> ExportMode.NORMAL
                        ExportMode.ERROR_UNION -> ExportMode.ERROR_CASE
                        ExportMode.ERROR_CASE -> throw RuntimeException()
                        null -> throw RuntimeException()
                    }, publicUse = publicUse)
                    refs.add(childName)
                    map.add(Pair(key, childName))

                    val child = schemas[childName]?.spec as? ObjectSpec
                    if (child != null) {
                        child.properties.removeAll {
                            it.name == propertyName
                        }
                        child.parents.add(name)

                        children.add(child)
                    }
                }

                val properties =
                    (children.getOrNull(0)?.properties ?: emptyList()).mapNotNull { prop ->
                        when (children.minOf { child ->
                            val matchingProp = child.properties.find { it.name == prop.name }
                            if (matchingProp != null && !matchingProp.asType.isNullable)
                                2 // REQUIRED
                            else if (matchingProp != null)
                                1 // OPTIONAL
                            else
                                0 // NOT PRESENT
                        }) {
                            2 -> prop
                            1 -> prop.asOptional
                            0 -> null
                            else -> throw RuntimeException()
                        }
                    }

                return ObjectSpec(
                    name = name,
                    asType = ClassName("io.portone.sdk.server.schemas", name),
                    discriminator = Discriminator(
                        propertyName = propertyName,
                        mapping = map,
                    ),
                    properties = properties.toMutableList(),
                    title = title,
                    description = description,
                )
            }
        }

        return when (val type = schema.getString("type")!!) {
            "boolean" -> MinimalSpec(
                name = name,
                asType = Boolean::class.asTypeName(),
                title = title,
                description = description,
            )
            "integer" -> MinimalSpec(
                name = name,
                asType = when (schema.getString("format")) {
                    "int32" -> Int::class.asTypeName()
                    "int64" -> Long::class.asTypeName()
                    null -> BigInteger::class.asTypeName()
                    else -> throw RuntimeException()
                },
                title = title,
                description = description,
            )
            "number" -> MinimalSpec(
                name = name,
                asType =  when (schema.getString("format")) {
                    "float" -> Float::class.asTypeName()
                    "double" -> Double::class.asTypeName()
                    else -> throw RuntimeException()
                },
                title = title,
                description = description,
            )
            "string" -> {
                val enum = schema.getArray("enum")
                val xPortoneEnum = schema.getObject("x-portone-enum")
                val format = schema.getString("format")
                if (enum != null) {
                    EnumSpec(
                        name = name,
                        asType = ClassName("io.portone.sdk.server.schemas", name),
                        values = enum.map { EnumValue(it.string, xPortoneEnum?.getObject(it.string)?.getString("title"), xPortoneEnum?.getObject(it.string)?.getString("description")) },
                        title = title,
                        description = description,
                    )
                } else if (format == "date-time") {
                    MinimalSpec(
                        name = name,
                        asType = Instant::class.asTypeName().copy(annotations = listOf(AnnotationSpec.builder(Serializable::class).addMember(
                            CodeBlock.of("%T::class", ClassName("io.portone.sdk.server.serializers", "InstantSerializer"))
                        ).build())),
                        title = title,
                        description = description,
                    )
                } else if (format == null) {
                    MinimalSpec(
                        name = name,
                        asType = String::class.asTypeName(),
                        title = title,
                        description = description,
                    )
                } else {
                    throw RuntimeException()
                }
            }
            "array" -> {
                val items = schema.getObject("items")!!
                val spec = visitSchema(items, name, publicUse = publicUse)
                MinimalSpec(
                    name = name,
                    asType = List::class.asTypeName().parameterizedBy(spec.asType),
                    title = title,
                    description = description,
                )
            }
            "object" -> {
                val rawProperties = schema.getObject("properties")
                if (rawProperties.isNullOrEmpty() && skipEmpty) {
                    MinimalSpec(
                        name = name,
                        asType = Unit::class.asTypeName(),
                        title = title,
                        description = description,
                    )
                } else if (rawProperties.isNullOrEmpty() && exportMode == null) {
                    MinimalSpec(
                        name = name,
                        asType = JsonObject::class.asTypeName(),
                        title = title,
                        description = description,
                    )
                } else {
                    val required = schema.getStringArray("required")?.toSet() ?: emptySet()

                    val properties = mutableListOf<Spec>()
                    for ((key, value) in rawProperties ?: emptyMap()) {
                        if (value is JsonObject) {
                            var spec = visitSchema(value, key, publicUse = publicUse)
                            if (!required.contains(key)) {
                                spec = spec.asOptional
                            }
                            properties.add(spec)
                        }
                    }

                    ObjectSpec(
                        name = name,
                        asType = ClassName("io.portone.sdk.server.schemas", name),
                        title = title,
                        description = description,
                        properties = properties,
                    )
                }
            }

            else -> throw RuntimeException("unknown type $type")
        }
    }

    private fun exportType(name: String, exportMode: ExportMode = ExportMode.NORMAL, skipEmpty: Boolean = false, publicUse: Boolean = true): TypeName {
        if (resolvedNames.contains(name)) {
            if (publicUse) {
                schemas[name]?.let { it.isPublic = true }
            }
            return ClassName("io.portone.sdk.server.schemas", name)
        }
        resolvedNames.add(name)
        val schema = documentSchemas?.getObject(name) ?: return ClassName("io.portone.sdk.server.schemas", name)
        val spec = visitSchema(schema, name, exportMode = exportMode, skipEmpty = skipEmpty, publicUse = publicUse)
        if (spec.asType != Unit::class.asTypeName()) {
            schemas[name] = Schema(spec, publicUse, exportMode)
        }
        return spec.asType
    }

    fun generateFiles() {
        visitDocument()

        generateSchemaFiles()
        generateClient()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun generateSchemaFiles() {
        for ((name, schema) in schemas) {
            val spec = schema.spec
            when (spec) {
                is ObjectSpec -> {
                    if (spec.discriminator != null) {
                        FileSpec.builder("io.portone.sdk.server.schemas", name)
                            .addType(
                                TypeSpec.interfaceBuilder(name).addModifiers(KModifier.SEALED)
                                    .also { builder -> spec.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                    .also { if (!schema.isPublic) it.addModifiers(KModifier.INTERNAL) }
                                    .addAnnotation(Serializable::class)
                                    .addAnnotation(AnnotationSpec.builder(JsonClassDiscriminator::class).addMember("%S", spec.discriminator.propertyName).build())
                                    .addProperties(spec.properties.map { prop ->
                                        PropertySpec.builder(prop.name, prop.asType)
                                            .also { builder -> prop.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                            .build()
                                    })
                                    .build()
                            )
                            .build().writeTo(targetDirectory)
                    } else if (spec.properties.isEmpty()) {
                        check(schema.exportMode != ExportMode.ERROR_CASE)
                        // 하위 클래스라 discriminator가 사라지니 속성이 남지 않은 이들

                        val parents = spec.parents.map { schemas[it]?.spec as ObjectSpec }

                        FileSpec.builder("io.portone.sdk.server.schemas", name)
                            .addType(
                                TypeSpec.objectBuilder(name).addModifiers(KModifier.DATA)
                                    .also { builder -> spec.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                    .also { if (!schema.isPublic) it.addModifiers(KModifier.INTERNAL) }
                                    .addAnnotation(Serializable::class)
                                    .addSuperinterfaces(parents.map { it.asType })
                                    .build()
                            )
                            .build().writeTo(targetDirectory)
                    } else {
                        val parents = spec.parents.map { schemas[it]?.spec as ObjectSpec }
                        val parentProperties = parents.flatMap { it.properties }
                        val serialName = parents.map { parent -> parent.discriminator!!.mapping.find { it.second == spec.name }!!.first }.also { serialNames ->
                            if (serialNames.any { it != serialNames.first() }) throw RuntimeException("serial name not all same")
                        }.firstOrNull()

                        FileSpec.builder("io.portone.sdk.server.schemas", name)
                            .addType(
                                TypeSpec.classBuilder(name).addModifiers(KModifier.DATA)
                                    .also { builder -> spec.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                    .let { if (schema.isPublic) it else it.addModifiers(KModifier.INTERNAL) }
                                    .addAnnotation(Serializable::class)
                                    .let { builder ->
                                        if (serialName != null) builder.addAnnotation(AnnotationSpec.builder(SerialName::class).addMember("%S", serialName).build())
                                        else builder
                                    }
                                    .addSuperinterfaces(parents.map { it.asType })
                                    .primaryConstructor(
                                        FunSpec.constructorBuilder()
                                            .addParameters(spec.properties.map { prop ->
                                                ParameterSpec.builder(prop.name, prop.asType).let { builder ->
                                                    if (prop.asType.isNullable)
                                                        builder.defaultValue("null")
                                                    else
                                                        builder
                                                }.build()
                                            })
                                            .build()
                                    )
                                    .addProperties(spec.properties.map { prop ->
                                        PropertySpec.builder(prop.name, prop.asType).initializer(prop.name)
                                            .also { builder ->
                                                if (parentProperties.any { it.name == prop.name }) builder.addModifiers(KModifier.OVERRIDE)
                                            }
                                            .also { builder -> prop.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                            .build()
                                    })
                                    .build()
                            )
                            .build().writeTo(targetDirectory)

                        if (schema.exportMode == ExportMode.ERROR_CASE) {
                            check(spec.name.endsWith("Error"))
                            val exceptionName = spec.name.substring(0, spec.name.length - "Error".length) + "Exception"
                            FileSpec.builder("io.portone.sdk.server.schemas", exceptionName)
                                .addType(
                                    TypeSpec.classBuilder(exceptionName)
                                        .superclass(Exception::class.java.asTypeName())
                                        .addSuperclassConstructorParameter("message")
                                        .also { builder -> spec.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                        .primaryConstructor(
                                            FunSpec.constructorBuilder()
                                                .addParameters(spec.properties.map { prop ->
                                                    ParameterSpec.builder(prop.name, prop.asType).also { builder ->
                                                        if (prop.asType.isNullable) builder.defaultValue("null")
                                                    }.build()
                                                })
                                                .build()
                                        )
                                        .addProperties(spec.properties.filter { it.name != "message" }.map { prop ->
                                            PropertySpec.builder(prop.name, prop.asType).initializer(prop.name)
                                                .also { builder -> prop.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                                .build()
                                        })
                                        .build()
                                )
                                .build().writeTo(targetDirectory)
                        }
                    }
                }
                is EnumSpec -> {
                    FileSpec.builder("io.portone.sdk.server.schemas", name)
                        .addType(
                            TypeSpec.enumBuilder(name)
                                .addAnnotation(Serializable::class)
                                .also { builder -> spec.joinSeparateParagraph?.let { builder.addKdoc(it) } }
                                .also { if (!schema.isPublic) it.addModifiers(KModifier.INTERNAL) }
                                .also { builder -> builder.enumConstants.putAll(spec.values.map { value ->
                                    Pair(value.name, TypeSpec.anonymousClassBuilder().also { builder -> value.joinSeparateParagraph?.let { builder.addKdoc(it) }}.build())
                                }) }
                                .build()
                        )
                        .build().writeTo(targetDirectory)
                }
                else -> throw RuntimeException()
            }

        }
    }

    private fun generateClient() {
        val clientClass = ClassName("io.portone.sdk.server", "PortOneApi")
        val httpClientClass = ClassName("io.ktor.client", "HttpClient")
        val okHttpClass = ClassName("io.ktor.client.engine.okhttp", "OkHttp")
        val jsonClass = ClassName("kotlinx.serialization.json", "Json")

        val classBuilder = TypeSpec.classBuilder(clientClass)
            .addSuperinterface(Closeable::class.asTypeName())
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("apiSecret", String::class.asTypeName())
                    .addParameter(ParameterSpec.builder("storeId", String::class.asTypeName().copy(nullable = true)).defaultValue("null").build())
                    .build()
            )
            .addProperty(PropertySpec.builder("apiSecret", String::class.asTypeName()).initializer("apiSecret").build())
            .addProperty(PropertySpec.builder("storeId", String::class.asTypeName().copy(nullable = true)).initializer("storeId").build())
            .addProperty(PropertySpec.builder("client", httpClientClass, KModifier.PRIVATE).initializer("%T(%T)", httpClientClass, okHttpClass).build())
            .addProperty(PropertySpec.builder("json", jsonClass, KModifier.PRIVATE).initializer("%T { ignoreUnknownKeys = true }", jsonClass).build())
            .addFunction(FunSpec.builder("close").addModifiers(KModifier.OVERRIDE).addStatement("client.close()").build())

        for (operation in operations) {
            val funBuilder = FunSpec.builder(operation.name).addModifiers(KModifier.SUSPEND)
                .addAnnotation(AnnotationSpec.builder(JvmName::class).addMember(""""${operation.name}Suspend"""").build())

            if (operation.returns != null && operation.success != Unit::class.asTypeName()) {
                funBuilder.returns(returnType = operation.success, kdoc = CodeBlock.of(operation.returns))
            } else {
                funBuilder.returns(returnType = operation.success)
            }

            operation.joinSeparateParagraph?.let { funBuilder.addKdoc(it) }

            var firstErrorCase = true
            for (errorCase in operation.errorCases) {
                val errorSpec = schemas[errorCase]!!.spec as ObjectSpec
                check(errorSpec.title?.contains('\n') != true) { errorSpec.title ?: "" }
                check(errorSpec.description?.contains('\n') != true) { errorSpec.description ?: "" }
                errorSpec.joinSingleLine
                    ?.let {
                        if (firstErrorCase) funBuilder.addKdoc("\n\n")
                        firstErrorCase = false
                        funBuilder.addKdoc("@throws $errorCase $it\n")
                    }
            }
            if (firstErrorCase) funBuilder.addKdoc("\n\n")
            firstErrorCase = false
            funBuilder.addKdoc("@throws UnrecognizedException API 응답이 알 수 없는 형식인 경우\n")

            for (param in operation.pathParams) {
                val paramBuilder = ParameterSpec.builder(param.name, param.asType.copy(annotations = param.asType.annotations.filterNot { (it.typeName as? ClassName)?.simpleName == "Serializable" }))
                if (param.asType.isNullable) {
                    paramBuilder.defaultValue("null")
                }
                param.joinSingleLine?.let { paramBuilder.addKdoc(it) }
                funBuilder.addParameter(paramBuilder.build())
            }

            for (param in operation.queryParams) {
                if (param.name == "storeId") continue
                val paramBuilder = ParameterSpec.builder(param.name, param.asType.copy(annotations = param.asType.annotations.filterNot { (it.typeName as? ClassName)?.simpleName == "Serializable" }))
                if (param.asType.isNullable) {
                    paramBuilder.defaultValue("null")
                }
                param.joinSingleLine?.let { paramBuilder.addKdoc(it) }
                funBuilder.addParameter(paramBuilder.build())
            }

            for (param in operation.bodyParams) {
                if (param.name == "storeId") continue
                val paramBuilder = ParameterSpec.builder(param.name, param.asType.copy(annotations = param.asType.annotations.filterNot { (it.typeName as? ClassName)?.simpleName == "Serializable" }))
                if (param.asType.isNullable) {
                    paramBuilder.defaultValue("null")
                }
                param.joinSingleLine?.let { paramBuilder.addKdoc(it) }
                funBuilder.addParameter(paramBuilder.build())
            }

            var pathPrefix = "https://api.portone.io"
            var stillPrefix = true
            val manualPathList = mutableListOf<String>()
            for (pathSegment in operation.path.split("/").dropWhile { it.isEmpty() }) {
                if (!pathSegment.startsWith('{')) {
                    if (stillPrefix) {
                        pathPrefix += '/' + pathSegment
                    } else {
                        manualPathList.add(pathSegment)
                    }
                } else {
                    manualPathList.add(pathSegment)
                    stillPrefix = false
                }
            }

            check(operation.method.uppercase() in listOf("GET", "POST", "PUT", "DELETE", "PATCH"))

            funBuilder.beginControlFlow("""val httpResponse = client.${operation.method.lowercase()}("${pathPrefix}")""")

            if (manualPathList.isNotEmpty() || operation.queryParams.isNotEmpty() || operation.bodyParams.isNotEmpty() && operation.method.uppercase() in listOf("GET", "DELETE")) {
                funBuilder.beginControlFlow("url")
                if (manualPathList.isNotEmpty()) {
                    funBuilder.addStatement("appendPathSegments(${manualPathList.joinToString(", ") { if (it.startsWith('{')) it.substring(1, it.length - 1) else """"$it"""" }})")
                }
                if (operation.queryParams.isNotEmpty()) {
                    funBuilder.addStatement(operation.queryParams.joinToString("\n") {
                        if (it.asType.isNullable) {
                            """if (${it.name} != null) parameters.append("${it.name}", ${it.name})"""
                        } else {
                            """parameters.append("${it.name}", ${it.name})"""
                        }
                    })
                }
                if (operation.bodyParams.isNotEmpty() && operation.method.uppercase() in listOf("GET", "DELETE")) {
                    funBuilder.addStatement("""parameters.append("requestBody", this@PortOneApi.json.encodeToString(%T(${operation.bodyParams.joinToString(", ") {
                        "${it.name} = ${it.name}"
                    }})))""", operation.body!!)
                }
                funBuilder.endControlFlow()
            }

            funBuilder.beginControlFlow("headers")
                .addStatement("""append(HttpHeaders.Authorization, "PortOne·${'$'}{this@PortOneApi.apiSecret}")""")
                .endControlFlow()
            funBuilder.addStatement("contentType(ContentType.Application.Json)")
            funBuilder.addStatement("accept(ContentType.Application.Json)")
            funBuilder.addStatement("""userAgent("portone-server-sdk-jvm/${'$'}{SDK_VERSION}")""")

            if (operation.method.uppercase() in listOf("POST", "PUT", "PATCH")) {
                if (operation.bodyParams.isNotEmpty()) {
                    funBuilder.addStatement("setBody(this@PortOneApi.json.encodeToString(%T(${operation.bodyParams.joinToString(", ") {
                        "${it.name} = ${it.name}"
                    }})))", operation.body!!)
                } else {
                    funBuilder.addStatement("""setBody("{}")""")
                }
            }

            funBuilder.endControlFlow()

            funBuilder.beginControlFlow("if (httpResponse.status.value !in 200..299)")
                .addStatement("val httpBody = httpResponse.body<String>()")

            check(operation.errorCases.isNotEmpty())
            funBuilder.beginControlFlow("val httpBodyDecoded = try")
                .addStatement("this.json.decodeFromString<%T>(httpBody)", operation.error)
                .endControlFlow()
                .beginControlFlow("catch (_: Exception)")
                .addStatement("""throw UnrecognizedException("Unrecognized API error: ${'$'}httpBody")""")
                .endControlFlow()
                .beginControlFlow("when (httpBodyDecoded)", operation.error)

            for (errorCase in operation.errorCases) {
                val exceptionName = errorCase.substring(0, errorCase.length - "Error".length) + "Exception"
                val properties = (schemas[errorCase]!!.spec as ObjectSpec).properties
                funBuilder.addStatement("is %T -> throw %T(${properties.joinToString(", ") {
                    "${it.name} = httpBodyDecoded.${it.name}"
                }})", ClassName("io.portone.sdk.server.schemas", errorCase), ClassName("io.portone.sdk.server.schemas", exceptionName))
            }

            funBuilder.endControlFlow()
                .endControlFlow()

            if (operation.success != Unit::class.asTypeName()) {
                funBuilder.addStatement("val httpBody = httpResponse.body<String>()")
                funBuilder.beginControlFlow("return try")
                    .addStatement("this.json.decodeFromString<%T>(httpBody)", operation.success)
                    .endControlFlow()
                    .beginControlFlow("catch (_: Exception)")
                    .addStatement("""throw UnrecognizedException("Unrecognized API response: ${'$'}httpBody")""")
                    .endControlFlow()
            }

            classBuilder.addFunction(funBuilder.build())

            val futureFunBuilder = FunSpec.builder("${operation.name}Future")
                .addAnnotation(AnnotationSpec.builder(JvmName::class).addMember("%S", operation.name).build())
                .returns(returnType = CompletableFuture::class.asTypeName().parameterizedBy(operation.success))
                .addKdoc("@suppress")
                .addParameters(funBuilder.parameters.map { it.toBuilder().also { it.kdoc.clear() }.build() })
                .addCode("return GlobalScope.future { ${operation.name}(${funBuilder.parameters.joinToString(", ") { it.name } }) }")

            classBuilder.addFunction(futureFunBuilder.build())
        }

        FileSpec.builder(clientClass)
            .addImport("io.ktor.client.request", "get", "post", "delete", "patch", "headers", "accept", "setBody")
            .addImport("io.ktor.client.call", "body")
            .addImport("io.ktor.http", "appendPathSegments", "HttpHeaders", "ContentType", "contentType", "userAgent")
            .addImport("kotlinx.serialization", "encodeToString")
            .addImport("kotlinx.serialization.json", "Json")
            .addImport("kotlinx.coroutines", "GlobalScope")
            .addImport("kotlinx.coroutines.future", "future")
            .addType(classBuilder.build())
            .build()
            .writeTo(targetDirectory)
    }
}

val JsonElement.string: String
    get() {
        val primitive = jsonPrimitive
        if (!primitive.isString) throw IllegalArgumentException("Element is not a string")
        return primitive.content
    }

val JsonElement.boolean: Boolean
    get() {
        val primitive = jsonPrimitive
        if (primitive.isString) throw IllegalArgumentException("Element is not a boolean")
        return when (primitive.content) {
            "true" -> true
            "false" -> false
            else -> throw IllegalArgumentException("Element is not a boolean")
        }
    }

fun JsonObject.getObject(name: String): JsonObject? = get(name)?.jsonObject

fun JsonObject.getArray(name: String): JsonArray? = get(name)?.jsonArray

fun JsonObject.getStringArray(name: String): List<String>? = getArray(name)?.map { it.string }

fun JsonObject.getString(name: String): String? = get(name)?.string

fun JsonObject.getBoolean(name: String): Boolean? = get(name)?.boolean
