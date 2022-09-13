package aaa.bivizul.a29project.buildSrc

object Version {

    const val compile_sdk_version = 33
    const val min_sdk_version = 24
    const val target_sdk_version = compile_sdk_version
    const val kotlin = "1.7.10"
    const val agp = "7.2.2"
    const val coroutines_version = "1.6.4"
    const val compose_multiplatform = "1.1.0"
    const val compose_version = "1.2.1"
    const val nav_compose = "2.4.1"
    const val compose_navigation = "2.5.1"
    const val jetbrains_binary_compatibility_validator = "0.10.0"
    const val coil_version = "2.2.0"
    const val activity_compose_version = "1.5.1"
    const val appcompat = "1.5.1"
    const val core_ktx_version = "1.8.0"
    const val material_design_version = "1.6.1"
    const val onesignal = "4.8.2"
    const val onesignal_plugin = "0.14.0"
    const val ktor_version = "2.1.0"
    const val napier_version = "2.6.1"
    const val settings_version = "0.9"
    const val serialization_version = "1.4.0"
}

object AndroidxSupport {
    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val core_ktx = "androidx.core:core-ktx:${Version.core_ktx_version}"
}

object JetpackCompose {
    const val foundation = "androidx.compose.foundation:foundation:${Version.compose_version}"
    const val material = "androidx.compose.material:material:${Version.compose_version}"
    const val navigation = "androidx.navigation:navigation-compose:${Version.nav_compose}"
}

object ComposeUtils {
    const val coil = "io.coil-kt:coil-compose:${Version.coil_version}"
    const val activity = "androidx.activity:activity-compose:${Version.activity_compose_version}"
    const val navigation = "androidx.navigation:navigation-compose:${Version.compose_navigation}"
}

object MaterialDesign {
    const val materialDesign = "com.google.android.material:material:${Version.material_design_version}"
}

object Ktor {
    const val core = "io.ktor:ktor-client-core:${Version.ktor_version}"
    const val logging = "io.ktor:ktor-client-logging:${Version.ktor_version}"
    const val cio = "io.ktor:ktor-client-cio:${Version.ktor_version}"
    const val negotiation = "io.ktor:ktor-client-content-negotiation:${Version.ktor_version}"
    const val json = "io.ktor:ktor-serialization-kotlinx-json:${Version.ktor_version}"
}

object Kotlin {
    const val validator = "org.jetbrains.kotlinx:binary-compatibility-validator:${Version.jetbrains_binary_compatibility_validator}"
    const val serializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.serialization_version}"
    const val serializationProtobuf =
        "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${Version.serialization_version}"
}

object KotlinCoroutines {
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines_version}"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines_version}"
}

object Util {
    const val napier = "io.github.aakira:napier:${Version.napier_version}"
    const val settings = "com.russhwolf:multiplatform-settings:${Version.settings_version}"
    const val onesignal = "com.onesignal:OneSignal:${Version.onesignal}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Version.coil_version}"
}