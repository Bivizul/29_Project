pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val jetbrainsComposeVersion = extra["compose.version"] as String
        val onesignalVersion = extra["onesignal.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(jetbrainsComposeVersion)
        id("com.onesignal.androidsdk.onesignal-gradle-plugin").version(onesignalVersion)
    }

}

rootProject.name = "29_Project"
include(":androidApp",":shared")