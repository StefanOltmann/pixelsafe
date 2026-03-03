import io.github.kdroidfilter.nucleus.desktop.application.dsl.AppImageCategory
import io.github.kdroidfilter.nucleus.desktop.application.dsl.CompressionLevel
import io.github.kdroidfilter.nucleus.desktop.application.dsl.ReleaseChannel
import io.github.kdroidfilter.nucleus.desktop.application.dsl.ReleaseType
import io.github.kdroidfilter.nucleus.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidGitVersion)
    alias(libs.plugins.nucleus)
}

group = "io.github.stefanoltmann"

androidGitVersion {
    format = "%tag%"
}

version = androidGitVersion.name()

logger.lifecycle("App version $version (Code: ${androidGitVersion.code()})")

kotlin {

    jvm()

    jvmToolchain(jdkVersion = 25)

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {

        outputModuleName = "app"

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

            /* Nucleus */
            implementation(libs.nucleus.aot)
            implementation(libs.nucleus.decoratedWindow)
        }

        jvmTest.dependencies {
            implementation(libs.kotlin.test)
        }

        wasmJsMain.dependencies {
            implementation(libs.cryptography.provider.webcrypto)
        }
    }
}

nucleus.application {

    mainClass = "MainKt"

    nativeDistributions {

        /* Package metadata */
        packageName = "PixelSafe"
        packageVersion = if (androidGitVersion.code() == 0)
            "1.0.0"
        else
            version.toString()
        description = "Steganography tool for PNG images"
        vendor = "Stefan Oltmann"
        copyright = "Copyright 2026 Stefan Oltmann"
        homepage = "https://stefan-oltmann.de"

        /* Nucleus features */
        cleanupNativeLibs = true
        enableAotCache = true
        compressionLevel = CompressionLevel.Maximum

        targetFormats(

            /* Windows Store */
            TargetFormat.AppX,

            /* Windows traditional */
            TargetFormat.Nsis,

            /* macOS */
            TargetFormat.Dmg,

            /* Linux */
            TargetFormat.AppImage
        )

        buildTypes {
            release {
                proguard {
                    version = "7.8.2"
                    isEnabled = true
                    optimize = true
                    obfuscate = false /* It's open source */
                    joinOutputJars = true
                    configurationFiles.from("src/jvmMain/proguard-rules.pro")
                }
            }
        }

        publish {
            github {
                enabled = true
                owner = "StefanOltmann"
                repo = "pixelsafe"
                token = System.getenv("GITHUB_TOKEN")
                channel = ReleaseChannel.Latest
                releaseType = ReleaseType.Release
            }
        }

        windows {

            iconFile.set(project.file("../icon/icon.ico"))

            /* Upgrade UUID — used by MSI for updates */
            upgradeUuid = "40964483-9971-41ca-8c51-e80de49aa623"

            /* Don't show a console window */
            console = false

            /* Per-user install (no admin required) */
            perUserInstall = true

            /* Start menu group */
            menuGroup = "Stefan Oltmann"

            /* Installation directory name */
            dirChooser = true

            appx {

                identityName = "StefanOltmann.PixelSafe"
                applicationId = "PixelSafe"
                displayName = "PixelSafe"
                publisher = "CN=1A06AF6C-2943-4BE6-BB85-12677BA3F28D"
                publisherDisplayName = "Stefan Oltmann"

                /* Languages */
                languages = listOf("en")

                /* Visual */
                backgroundColor = "#001F3F"
                showNameOnTiles = true

                /* Tile logos (PNG) */
                storeLogo.set(project.file("packaging/appx/StoreLogo.png"))
                square44x44Logo.set(project.file("packaging/appx/Square44x44Logo.png"))
                square150x150Logo.set(project.file("packaging/appx/Square150x150Logo.png"))
                wide310x150Logo.set(project.file("packaging/appx/Wide310x150Logo.png"))

                /* Store build options */
                addAutoLaunchExtension = false
                setBuildNumber = true
            }
        }

        macOS {
            iconFile.set(project.file("../icon/icon.icns"))
        }

        linux {

            iconFile.set(project.file("../icon/icon.png"))

            appImage {

                /* XDG category */
                category = AppImageCategory.Utility

                /* Desktop entry fields */
                genericName = packageName
                synopsis = description
            }
        }
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
        writer.println("const val APP_VERSION: String = \"${project.version}\"")

        writer.flush()
    }
}
// endregion
