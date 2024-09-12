package io.portone.openapi

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files

abstract class SavePatchTask : DefaultTask() {
    @get:InputDirectory
    abstract val inputDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty


    companion object {
        private const val TAG_NAME = "patch-base"
    }

    @TaskAction
    fun patchCode() {
        val inputDirectory = this.inputDirectory.get().asFile.toPath()
        val outputDirectory = this.outputDirectory.get().asFile.toPath()

        Files.createDirectories(outputDirectory)

        project.exec {
            it.workingDir = inputDirectory.toFile()
            it.executable = "git"
            it.args = listOf("format-patch", "--zero-commit", "--no-stat", "--minimal", "-N", "-o", outputDirectory.toString(), TAG_NAME)
        }

    }
}
