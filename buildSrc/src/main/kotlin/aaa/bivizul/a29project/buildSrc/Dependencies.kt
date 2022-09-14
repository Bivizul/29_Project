package aaa.bivizul.a29project.buildSrc

object Version {

    const val coroutines_version = "1.6.4"
    const val compose_multiplatform = "1.1.0"
    const val compose_version = "1.2.1"
    const val nav_compose = "2.4.1"
    const val accompanist_version = "0.26.3-beta"
    const val kamel_version = "0.4.0"
    const val compose_navigation_version = "2.5.1"
    const val activity_compose_version = "1.5.1"
    const val appcompat = "1.5.1"
    const val core_ktx_version = "1.8.0"
    const val material_design_version = "1.6.1"
    const val onesignal_version = "4.8.2"
    const val onesignal_plugin = "0.14.0"
    const val ktor_version = "2.1.0"
    const val advancedwebview_version = "v3.2.1"
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
    const val activity = "androidx.activity:activity-compose:${Version.activity_compose_version}"
    const val navigation = "androidx.navigation:navigation-compose:${Version.compose_navigation_version}"
    const val accompanistWebview = "com.google.accompanist:accompanist-webview:${Version.accompanist_version}"
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
    const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.serialization_version}"
}

object KotlinCoroutines {
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines_version}"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines_version}"
}

object Util {
    const val settings = "com.russhwolf:multiplatform-settings:${Version.settings_version}"
    const val onesignal = "com.onesignal:OneSignal:${Version.onesignal_version}"
    const val advancedWebView = "com.github.delight-im:Android-AdvancedWebView:${Version.advancedwebview_version}"
    const val kamel = "com.alialbaali.kamel:kamel-image:${Version.kamel_version}"
}