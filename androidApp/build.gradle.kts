import aaa.bivizul.a29project.buildSrc.*

plugins {
    kotlin("android")
//    kotlin("plugin.parcelize")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")

    id("com.onesignal.androidsdk.onesignal-gradle-plugin")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "aaa.bivizul.a28project.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(AndroidxSupport.appcompat)
    implementation(MaterialDesign.materialDesign)
    implementation(ComposeUtils.activity)
    implementation(compose.foundation)
    implementation(Util.onesignal)
    implementation(Kotlin.serializationJson)
}