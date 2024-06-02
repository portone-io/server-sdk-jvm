import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.net.URI

plugins {
    `java-library`
    signing

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

group = "io.portone"
description = "JVM library for integrating PortOne payment infrastructure."

val githubRef = System.getenv("GITHUB_REF")
if (githubRef != null && githubRef.startsWith("refs/tags/v")) {
    version = githubRef.substring("refs/tags/v".length)
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
        freeCompilerArgs.addAll("-Xjdk-release=11", "-Xjsr305=strict")
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

repositories {
    mavenCentral()
}

dependencies {
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin.test)
        }
    }
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
        sourceLink {
            localDirectory = projectDir
            remoteUrl =
                run {
                    val githubSha = System.getenv("GITHUB_SHA")
                    URI(
                        "https://github.com/portone-io/server-sdk-jvm/${if (githubSha == null) "tree/main" else "blob/$githubSha"}/common",
                    ).toURL()
                }
        }
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
