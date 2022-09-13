import aaa.bivizul.a29project.buildSrc.*

plugins {
    kotlin("multiplatform")
//    kotlin("plugin.parcelize")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")

//    id("com.onesignal.androidsdk.onesignal-gradle-plugin")

}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting{
            dependencies{
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
//                implementation(compose.preview)

//                implementation(JetpackCompose.navigation)
                implementation(Ktor.core)
                implementation(Ktor.logging)
                implementation(Ktor.negotiation)
                implementation(Ktor.json)
                implementation(Ktor.cio)
                implementation(KotlinCoroutines.core)
//                implementation(Util.napier)
                implementation(Kotlin.serializationJson)
                implementation(Kotlin.serializationProtobuf)
                implementation(Util.settings)
                implementation(Util.onesignal)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting{
            dependencies{
                implementation(AndroidxSupport.appcompat)
                implementation(AndroidxSupport.core_ktx)
                implementation(Coil.coil)
                implementation(ComposeUtils.coil)
                implementation(JetpackCompose.material)
                implementation(JetpackCompose.navigation)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}