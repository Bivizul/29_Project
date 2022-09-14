package aaa.bivizul.a29project.ui.web

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal actual fun WebViewImpl(
    state: WebViewState,
    modifier: Modifier,
    navigator: WebViewNavigator,
    activity: Any
) {
    WebView(
        state,
        modifier,
        true,
        navigator,
        activity = activity,
        onCreated = {
            it.settings.javaScriptEnabled = true
        },
        onDispose = {
            it.destroy()
        }
    )
}