package io.portone.openapi

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateVersionCodeTask : DefaultTask() {
    @get:Input
    abstract val version: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generateVersionCode() {
        val outputDirectory = this.outputDirectory.get().asFile.toPath()

        FileSpec.builder("io.portone.sdk.server", "Version")
            .addProperty(PropertySpec.builder("SDK_VERSION", String::class, KModifier.CONST, KModifier.INTERNAL).initializer("%S", version.get()).build())
            .build().writeTo(outputDirectory)
    }
}
