//package aaa.bivizul.a29project.ui.web
//
//import android.graphics.Bitmap
//import android.util.Log
//import android.webkit.WebResourceRequest
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material.LinearProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//import com.google.accompanist.web.*
//
//@Composable
//actual fun WebContent(
//
//) {
//    val navigator = rememberWebViewNavigator()
//    val url by remember {
//        mutableStateOf("https://google.com")
//    }
//    var state = rememberWebViewState(url = url)
//
//    var loadingState = state.loadingState
//    if (loadingState is LoadingState.Loading) {
//        LinearProgressIndicator(
//            progress = loadingState.progress,
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//
//    val webClient = remember {
//        object : AccompanistWebViewClient() {
//            override fun onPageStarted(
//                view: WebView?,
//                url: String?,
//                favicon: Bitmap?
//            ) {
//                super.onPageStarted(view, url, favicon)
//                Log.d("Accompanist WebView", "Page started loading for $url")
//            }
//        }
//    }
//
////    WebView(
////        state = state,
//////        modifier = Modifier.weight(1f),
////        modifier = Modifier.fillMaxSize(),
////        navigator = navigator,
////        onCreated = { webView ->
////            webView.settings.javaScriptEnabled = true
////        },
////        client = webClient
////    )
//
//    AndroidView(
//        factory = {
//            WebView(it).apply {
//                webViewClient = object : WebViewClient() {
//                    override fun shouldOverrideUrlLoading(
//                        view: WebView?,
//                        request: WebResourceRequest?
//                    ): Boolean {
//                        return false
//                    }
//                }
//            }
//        }, update = {
//            it.loadUrl(url)
//        }
//    )
//
//}