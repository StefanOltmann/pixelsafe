import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

val wasmActivated = true

kotlin {

    jvm()

    if (wasmActivated) {

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
            packageName = "de.stefan_oltmann.pixelsafe"
            packageVersion = "1.0.0"
        }
    }
}
