package io.portone.openapi

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.relativeTo

abstract class PatchCodeTask : DefaultTask() {
    @get:InputDirectory
    abstract val originDirectory: DirectoryProperty

    @get:InputDirectory
    abstract val patchesDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    companion object {
        private const val TAG_NAME = "patch-base"
    }

    @TaskAction
    fun patchCode() {
        val originDirectory = this.originDirectory.get().asFile.toPath()
        val patchesDirectory = this.patchesDirectory.get().asFile.toPath()
        val outputDirectory = this.outputDirectory.get().asFile.toPath()

        Files.createDirectories(outputDirectory)

        project.exec {
            it.workingDir = outputDirectory.toFile()
            it.executable = "git"
            it.args = listOf("init")
            it.isIgnoreExitValue = true
        }

        project.exec {
            it.workingDir = outputDirectory.toFile()
            it.executable = "git"
            it.args = listOf("config", "commit.gpgSign", "false")
        }

        project.exec {
            it.workingDir = project.rootProject.projectDir
            it.executable = "git"
            it.args = listOf("tag", TAG_NAME)
        }

        project.exec {
            it.workingDir = project.rootProject.projectDir
            it.executable = "git"
            // use joinToString("/") because git filter-repo doesn't support '\' (windows path separator) in filter
            it.args = listOf("filter-repo", "--force", "--subdirectory-filter", originDirectory.relativeTo(project.rootProject.projectDir.toPath()).joinToString("/"), "--target", outputDirectory.toString())
        }

        project.exec {
            it.workingDir = project.rootProject.projectDir
            it.executable = "git"
            it.args = listOf("tag", "-d", TAG_NAME)
        }

        project.exec {
            it.workingDir = outputDirectory.toFile()
            it.executable = "git"
            it.args = listOf("checkout", TAG_NAME)
        }

        project.exec {
            it.workingDir = outputDirectory.toFile()
            it.executable = "git"
            it.args = listOf("am", "--abort")
            it.standardOutput = ByteArrayOutputStream()
            it.errorOutput = ByteArrayOutputStream()
            it.isIgnoreExitValue = true
        }

        val patchFiles = patchesDirectory.listDirectoryEntries("*.patch").sorted()

        project.exec {
            it.workingDir = outputDirectory.toFile()
            it.executable = "git"
            it.args = listOf("am", "--3way", *patchFiles.map { it.toString() }.toTypedArray())
        }
    }
}
