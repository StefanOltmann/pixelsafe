import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidGitVersion)
    alias(libs.plugins.hydraulicConveyor)
}

group = "io.github.stefanoltmann"

androidGitVersion {
    format = "%tag%"
}

version = androidGitVersion.name()

logger.lifecycle("App version $version (Code: ${androidGitVersion.code()})")

val buildTarget: String? = System.getenv("BUILD_TARGET") ?: "desktop"

kotlin {

    jvm()

    jvmToolchain(jdkVersion = 17)

    if (buildTarget == "web") {

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {

            moduleName = "app"

            browser {

                val rootDirPath = project.rootDir.path
                val projectDirPath = project.projectDir.path

                commonWebpackConfig {

                    outputFileName = "app.js"

                    devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                        static = (static ?: mutableListOf()).apply {
                            // Serve sources to debug inside browser
                            add(rootDirPath)
                            add(projectDirPath)
                        }
                    }
                }
            }

            binaries.executable()
        }
    }

    sourceSets {

        sourceSets["commonMain"].kotlin.srcDirs(file("build/generated/source/"))

        commonMain.dependencies {

            /* Compose */
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)

            /* FileKit */
            implementation(libs.filekit.core)
            implementation(libs.filekit.dialogs)
            implementation(libs.filekit.dialogs.compose)

            /* Skiko */
            implementation(libs.skiko)

            /* Cryptography */
            implementation(libs.cryptography.core)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.cryptography.provider.jdk)
        }

        jvmTest.dependencies {
            implementation(libs.kotlin.test)
        }

        wasmJsMain.dependencies {
            implementation(libs.cryptography.provider.webcrypto)
        }
    }
}

compose.desktop {

    application {

        mainClass = "MainKt"

        nativeDistributions {

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            packageName = "PixelSafe"

            if (androidGitVersion.code() == 0) {

                /* Values for the dev version. */
                packageVersion = "1.0.0"

            } else {

                packageVersion = version.toString()
            }

            macOS {
                iconFile.set(project.file("../icon/icon.icns"))
            }

            windows {
                iconFile.set(project.file("../icon/icon.ico"))
            }

            linux {
                iconFile.set(project.file("../icon/icon.png"))
            }
        }
    }
}

dependencies {

    /*
     * Workaround for a bug in Hydraulic Conveyor 18:
     * It does not support wasmJS target.
     */
    if (buildTarget == "desktop") {

        linuxAmd64(compose.desktop.linux_x64)
        macAmd64(compose.desktop.macos_x64)
        macAarch64(compose.desktop.macos_arm64)
        windowsAmd64(compose.desktop.windows_x64)
    }
}

// region AppBuildConfig
project.afterEvaluate {

    logger.lifecycle("Generate AppBuildConfig.kt")

    val outputDir = layout.buildDirectory.file("generated/source/").get().asFile

    outputDir.mkdirs()

    val file = File(outputDir.absolutePath, "AppBuildConfig.kt")

    file.printWriter().use { writer ->

        writer.println("/** Current app version. */")
        writer.println("const val APP_VERSION: String = \"${project.rootProject.version}\"")

        writer.flush()
    }
}
// endregion

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
// endregion
