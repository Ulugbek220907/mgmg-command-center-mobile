import java.util.Properties

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

val hasAndroidSdk: Boolean = run {
    val envHome = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
    if (!envHome.isNullOrBlank() && file(envHome).exists()) return@run true
    val localProp = rootProject.file("local.properties")
    if (localProp.exists()) {
        val props = Properties().apply { localProp.inputStream().use { load(it) } }
        val sdkDir = props.getProperty("sdk.dir")
        if (!sdkDir.isNullOrBlank() && file(sdkDir).exists()) return@run true
    }
    val defaultSdk = file("${System.getProperty("user.home")}/AppData/Local/Android/Sdk")
    if (defaultSdk.exists()) {
        return@run true
    }
    false
}

if (hasAndroidSdk) {
    apply(plugin = "com.android.application")
}

kotlin {
    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    if (hasAndroidSdk) {
        androidTarget {
            compilations.all {
                compileTaskProvider.configure {
                    compilerOptions {
                        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                    }
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.9.0")
            }
        }

        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.withType<Test> {
    systemProperty("file.encoding", "UTF-8")
}

if (hasAndroidSdk) {
    configure<com.android.build.api.dsl.ApplicationExtension> {
        namespace = "com.mgm.commandcenter"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.mgm.commandcenter"
            minSdk = 24
            targetSdk = 34
            versionCode = 42
            versionName = "1.0.4"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    kotlin.sourceSets.getByName("androidMain").dependencies {
        implementation("androidx.activity:activity-compose:1.9.3")
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("androidx.core:core-ktx:1.13.1")
    }
}

compose.desktop {
    application {
        mainClass = "com.mgm.commandcenter.MainKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "com.mgm.commandcenter"
            packageVersion = "1.0.4"
        }
    }
}
