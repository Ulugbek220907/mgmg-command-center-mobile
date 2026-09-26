plugins {
    id("org.jetbrains.kotlin.multiplatform") version "2.0.21" apply false
    id("org.jetbrains.compose") version "1.7.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    id("com.android.application") version "8.5.2" apply false
}

tasks.register("test") {
    group = "verification"
    description = "Runs multiplatform unit tests"
    dependsOn(":composeApp:desktopTest")
}
