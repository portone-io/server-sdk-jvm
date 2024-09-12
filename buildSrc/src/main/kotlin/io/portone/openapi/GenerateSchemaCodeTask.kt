package io.portone.openapi

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

abstract class GenerateSchemaCodeTask : DefaultTask() {
    @get:InputFile
    abstract val inputFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generateSchemaCode() {
        val document = Json.parseToJsonElement(Files.readString(inputFile.get().asFile.toPath())).jsonObject
        SchemaGenerator(document, listOf(
            "/payments",
            "/payment-schedules",
            "/identity-verifications",
            "/billing-keys",
            "/cash-receipts",
            "/kakaopay",
        ), outputDirectory.get().asFile.toPath()).generateFiles()
    }
}
