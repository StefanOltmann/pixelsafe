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

        commonMain.dependencies {

            /* Compose */
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)

            /* FileKit */
            implementation("io.github.vinceglb:filekit-core:0.10.0-beta02")
            implementation("io.github.vinceglb:filekit-dialogs:0.10.0-beta02")
            implementation("io.github.vinceglb:filekit-dialogs-compose:0.10.0-beta02")

            /* Skiko */
            implementation("org.jetbrains.skiko:skiko:0.9.4")
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }

        jvmTest.dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test:2.1.20")
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

// region Work around temporary Compose bugs.
configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
// endregion
