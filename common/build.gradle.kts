import com.vanniktech.maven.publish.SonatypeHost
import io.portone.openapi.GenerateSchemaCodeTask
import io.portone.openapi.GenerateVersionCodeTask
import io.portone.openapi.PatchCodeTask
import io.portone.openapi.SavePatchTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.ByteArrayOutputStream
import java.net.URI
import java.nio.charset.StandardCharsets

plugins {
    `java-library`
    signing

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

group = "io.portone"
description = "JVM library for integrating PortOne payment infrastructure."

version =
    run {
        val output = ByteArrayOutputStream()
        exec {
            workingDir = rootProject.projectDir
            executable = "git"
            args = listOf("describe", "--dirty", "--tags", "--match", "v*", "--first-parent")
            standardOutput = output
        }
        output.toString(StandardCharsets.UTF_8).trimEnd('\n', '\r').substring(1)
    }

val generateVersionCode =
    tasks.register<GenerateVersionCodeTask>("generateVersionCode") {
        version = project.version.toString()
        outputDirectory = layout.buildDirectory.dir("generated/sources/versionCode/kotlin/main")
        outputs.upToDateWhen { false }
    }

val generateSchemaCode =
    tasks.register<GenerateSchemaCodeTask>("generateSchemaCode") {
        inputFile = file("../openapi/portone-v2-openapi.json")
        outputDirectory.set(layout.buildDirectory.dir("generated/sources/schemaCode/kotlin/main"))
    }

val patchCode =
    tasks.register<PatchCodeTask>("patchCode") {
        originDirectory = generateSchemaCode.flatMap { it.outputDirectory }
        patchesDirectory = file("patches")
        outputDirectory.set(layout.buildDirectory.dir("generated/sources/patchedCode/kotlin/main"))
        outputs.upToDateWhen { false }
    }

val savePatch =
    tasks.register<SavePatchTask>("savePatch") {
        inputDirectory.set(layout.buildDirectory.dir("generated/sources/patchedCode/kotlin/main"))
        outputDirectory = file("patches")
        outputs.upToDateWhen { false }
    }

sourceSets {
    main {
        kotlin {
            srcDir(generateVersionCode)
            srcDir(patchCode)
        }
    }
}

tasks.compileKotlin {
    dependsOn(generateSchemaCode)
}

tasks.withType<KotlinJvmCompile>().configureEach {
    jvmTargetValidationMode = JvmTargetValidationMode.ERROR
}

kotlin {
    jvmToolchain(21)
    explicitApi()
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
        progressiveMode = true
        allWarningsAsErrors = true
        freeCompilerArgs.addAll(
            "-Xjdk-release=11",
            "-Xjsr305=strict",
            "-opt-in=kotlinx.coroutines.DelicateCoroutinesApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.compileJava {
    options.javaModuleVersion = provider { version as String }
    options.compilerArgumentProviders.add(
        CommandLineArgumentProvider {
            listOf("--patch-module", "io.portone.sdk.server=${sourceSets["main"].output.asPath}")
        },
    )
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
}

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin.test)
        }
    }
}

tasks.apiBuild {
    inputJar.value(tasks.jar.flatMap { it.archiveFile })
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "PortOne Server SDK for JVM",
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "PortOne",
            "Sealed" to "true",
        )
    }
}

tasks.withType<DokkaTask> {
    moduleName = "PortOne Server SDK for JVM"

    dokkaSourceSets.configureEach {
        jdkVersion = 22
        includes.from("Module.md")
        suppressGeneratedFiles = false
    }
}

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier = "html-docs"
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates(artifactId = "server-sdk")

    pom {
        name = "PortOne Server SDK for JVM"
        description = project.description
        url = "https://github.com/portone-io/server-sdk-jvm"
        inceptionYear = "2024"

        licenses {
            license {
                name = "Apache License 2.0"
                url = "https://apache.org/licenses/LICENSE-2.0"
                distribution = "repo"
            }
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/mit"
                distribution = "repo"
            }
        }

        organization {
            name = "PortOne"
            url = "https://portone.io"
        }

        developers {
            developer {
                id = "fina"
                email = "fina@portone.io"
                organization = "PortOne"
                organizationUrl = "https://portone.io"
            }
            developer {
                id = "kai"
                email = "kai@portone.io"
                organization = "PortOne"
                organizationUrl = "https://portone.io"
            }
            developer {
                id = "cosmo"
                email = "cosmo@portone.io"
                organization = "PortOne"
                organizationUrl = "https://portone.io"
            }
        }

        scm {
            connection = "scm:git:https://github.com/portone-io/server-sdk-jvm.git"
            developerConnection = "scm:git:https://github.com/portone-io/server-sdk-jvm.git"
            url = "https://github.com/portone-io/server-sdk-jvm"
        }

        issueManagement {
            system = "GitHub"
            url = "https://github.com/portone-io/server-sdk-jvm/issues"
        }

        ciManagement {
            system = "GitHub Actions"
            url = "https://github.com/portone-io/server-sdk-jvm/actions"
        }
    }
}
