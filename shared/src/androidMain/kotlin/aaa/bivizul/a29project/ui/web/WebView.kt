package aaa.bivizul.a29project.ui.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A wrapper around the Android View WebView to provide a basic WebView composable.
 *
 * If you require more customisation you are most likely better rolling your own and using this
 * wrapper as an example.
 *
 * @param state The webview state holder where the Uri to load is defined.
 * @param captureBackPresses Set to true to have this Composable capture back presses and navigate
 * the WebView back.
 * @param navigator An optional navigator object that can be used to control the WebView's
 * navigation from outside the composable.
 * @param onCreated Called when the WebView is first created, this can be used to set additional
 * settings on the WebView. WebChromeClient and WebViewClient should not be set here as they will be
 * subsequently overwritten after this lambda is called.
 * @param client Provides access to WebViewClient via subclassing
 * @param chromeClient Provides access to WebChromeClient via subclassing
 * @sample com.google.accompanist.sample.webview.BasicWebViewSample
 */
@Composable
internal fun WebView(
    state: WebViewState,
    modifier: Modifier = Modifier,
    captureBackPresses: Boolean = true,
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    activity: Any,
    onCreated: (WebView) -> Unit = {},
    onDispose: (WebView) -> Unit = {},
    client: AccompanistWebViewClient = remember { AccompanistWebViewClient(activity = activity) },
    chromeClient: AccompanistWebChromeClient = remember { AccompanistWebChromeClient(spbkactivity = activity) }
) {
    var webView by remember { mutableStateOf<WebView?>(null) }

    BackHandler(captureBackPresses && navigator.canGoBack) {
        webView?.goBack()
    }

    LaunchedEffect(webView, navigator) {
        with(navigator) { webView?.handleNavigationEvents() }
    }

    val currentOnDispose by rememberUpdatedState(onDispose)

    webView?.let { it ->
        DisposableEffect(it) {
            onDispose { currentOnDispose(it) }
        }
    }

    // Set the state of the client and chrome client
    // This is done internally to ensure they always are the same instance as the
    // parent Web composable
    client.state = state
    client.navigator = navigator
    chromeClient.state = state

    val runningInPreview = LocalInspectionMode.current

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                onCreated(this)

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webChromeClient = chromeClient
                webViewClient = client
            }.also { webView = it }
        },
        modifier = modifier
    ) { view ->
        // AndroidViews are not supported by preview, bail early
        if (runningInPreview) return@AndroidView

        //
        setSettings(view)
        //

        when (val content = state.content) {
            is WebContent.Url -> {
                val url = content.url

                if (url.isNotEmpty() && url != view.url) {
                    view.loadUrl(url, content.additionalHttpHeaders.toMutableMap())
                }
            }
            is WebContent.Data -> {
                view.loadDataWithBaseURL(content.baseUrl, content.data, null, "utf-8", null)
            }
        }

        navigator.canGoBack = view.canGoBack()
        navigator.canGoForward = view.canGoForward()
    }
}

// my custom////////////////////////////////////////////////////////////////////
@SuppressLint("SetJavaScriptEnabled")
private fun setSettings(webView: WebView) {
    val webSettings = webView.settings
    webSettings.javaScriptEnabled = true
    webSettings.loadWithOverviewMode = true
    webSettings.allowFileAccess = true
    webSettings.domStorageEnabled = true
    webSettings.builtInZoomControls = true
    webSettings.displayZoomControls = false
    webSettings.useWideViewPort = true
    webSettings.setSupportZoom(true)
    webSettings.userAgentString = webSettings.userAgentString.replace("; wv", "")

    //////////////////////
    webSettings.setPluginState(WebSettings.PluginState.OFF)
    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)
    webSettings.setAllowContentAccess(true)

}

/**
 * AccompanistWebViewClient
 *
 * A parent class implementation of WebViewClient that can be subclassed to add custom behaviour.
 *
 * As Accompanist Web needs to set its own web client to function, it provides this intermediary
 * class that can be overriden if further custom behaviour is required.
 */
open class AccompanistWebViewClient(activity: Any) : WebViewClient() {
    open lateinit var state: WebViewState
        internal set
    open lateinit var navigator: WebViewNavigator
        internal set


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        state.loadingState = LoadingState.Loading(0.0f)
        state.errorsForCurrentRequest.clear()
        state.pageTitle = null
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        state.loadingState = LoadingState.Finished
        navigator.canGoBack = view?.canGoBack() ?: false
        navigator.canGoForward = view?.canGoForward() ?: false
    }

    override fun doUpdateVisitedHistory(
        view: WebView?,
        url: String?,
        isReload: Boolean
    ) {
        super.doUpdateVisitedHistory(view, url, isReload)
        // WebView will often update the current url itself.
        // This happens in situations like redirects and navigating through
        // history. We capture this change and update our state holder url.
        // On older APIs (28 and lower), this method is called when loading
        // html data. We don't want to update the state in this case as that will
        // overwrite the html being loaded.
        if (url != null &&
            !url.startsWith("data:text/html") &&
            state.content.getCurrentUrl() != url
        ) {
            state.content = state.content.withUrl(url)
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)

        if (error != null) {
            state.errorsForCurrentRequest.add(WebViewError(request, error))
        }
    }

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        // Override all url loads to make the single source of truth
        // of the URL the state holder Url
        request?.let {
            state.content = state.content.withUrl(it.url.toString())
        }
        return true
    }
}

/**
 * AccompanistWebChromeClient
 *
 * A parent class implementation of WebChromeClient that can be subclassed to add custom behaviour.
 *
 * As Accompanist Web needs to set its own web client to function, it provides this intermediary
 * class that can be overriden if further custom behaviour is required.
 */
@Suppress("DEPRECATION")
open class AccompanistWebChromeClient(spbkactivity: Any) : WebChromeClient() {

    //////////////////
    val activity = spbkactivity as Activity
    var filePathCallback: ValueCallback<Array<Uri>>? = null
    private val REQUEST_CODE = 100
    ////////////////

    open lateinit var state: WebViewState
        internal set

    /////////////
    private var mCustomView: View? = null
    private var mCustomViewCallback: CustomViewCallback? = null
    private var mOriginalOrientation = 0
    private var mOriginalSystemUiVisibility = 0
///////////////////////////

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        state.pageTitle = title
    }

    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (state.loadingState is LoadingState.Finished) return
        state.loadingState = LoadingState.Loading(newProgress / 100.0f)
    }

    //////////////////////
    override fun onShowFileChooser(
        view: WebView,
        filePath: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        filePathCallback = filePath
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
        activity.startActivityForResult(intent, REQUEST_CODE)
        return true
    }

    fun getFilePath():ValueCallback<Array<Uri>>{
        return filePathCallback!!
    }

    override fun getDefaultVideoPoster(): Bitmap? {
        return if (mCustomView == null) {
            null
        } else BitmapFactory.decodeResource(activity.applicationContext.resources, 2130837573)
    }

    //////////////// good
    override fun onHideCustomView() {
        (activity.window.decorView as FrameLayout).removeView(mCustomView)
        mCustomView = null
        activity.window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
        activity.requestedOrientation = mOriginalOrientation
        mCustomViewCallback!!.onCustomViewHidden()
        mCustomViewCallback = null
    }

    override fun onShowCustomView(
        paramView: View?,
        paramCustomViewCallback: CustomViewCallback?,
    ) {
        if (mCustomView != null) {
            onHideCustomView()
            return
        }
        mCustomView = paramView
        mOriginalSystemUiVisibility = activity.window.decorView.systemUiVisibility
        mOriginalOrientation = activity.requestedOrientation
        mCustomViewCallback = paramCustomViewCallback
        (activity.window.decorView as FrameLayout).addView(
            mCustomView,
            FrameLayout.LayoutParams(-1, -1)
        )
        activity.window.decorView.systemUiVisibility =
            3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }



}

actual sealed class WebContent {
    data class Url(
        val url: String,
        val additionalHttpHeaders: Map<String, String> = emptyMap(),
    ) : WebContent()

    data class Data(val data: String, val baseUrl: String? = null) : WebContent()

    fun getCurrentUrl(): String? {
        return when (this) {
            is Url -> url
            is Data -> baseUrl
        }
    }
}

internal fun WebContent.withUrl(url: String) = when (this) {
    is WebContent.Url -> copy(url = url)
    else -> WebContent.Url(url)
}


/**
 * A state holder to hold the state for the WebView. In most cases this will be remembered
 * using the rememberWebViewState(uri) function.
 */
@Stable
actual class WebViewState actual constructor(webContent: WebContent) {
    /**
     *  The content being loaded by the WebView
     */
    var content: WebContent by mutableStateOf(webContent)

    /**
     * Whether the WebView is currently [LoadingState.Loading] data in its main frame (along with
     * progress) or the data loading has [LoadingState.Finished]. See [LoadingState]
     */
    var loadingState: LoadingState by mutableStateOf(LoadingState.Initializing)
        internal set

    /**
     * Whether the webview is currently loading data in its main frame
     */
    val isLoading: Boolean
        get() = loadingState !is LoadingState.Finished

    /**
     * The title received from the loaded content of the current page
     */
    var pageTitle: String? by mutableStateOf(null)
        internal set

    /**
     * the favicon received from the loaded content of the current page
     */
    var pageIcon: Bitmap? by mutableStateOf(null)
        internal set

    /**
     * A list for errors captured in the last load. Reset when a new page is loaded.
     * Errors could be from any resource (iframe, image, etc.), not just for the main page.
     * For more fine grained control use the OnError callback of the WebView.
     */
    val errorsForCurrentRequest: SnapshotStateList<WebViewError> = mutableStateListOf()
}

/**
 * Allows control over the navigation of a WebView from outside the composable. E.g. for performing
 * a back navigation in response to the user clicking the "up" button in a TopAppBar.
 *
 * @see [rememberWebViewNavigator]
 */
@Stable
actual class WebViewNavigator actual constructor(private val coroutineScope: CoroutineScope) {

    private enum class NavigationEvent { BACK, FORWARD, RELOAD, STOP_LOADING }

    private val navigationEvents: MutableSharedFlow<NavigationEvent> = MutableSharedFlow()

    // Use Dispatchers.Main to ensure that the webview methods are called on UI thread
    internal suspend fun WebView.handleNavigationEvents(): Nothing = withContext(Dispatchers.Main) {
        navigationEvents.collect { event ->
            when (event) {
                NavigationEvent.BACK -> goBack()
                NavigationEvent.FORWARD -> goForward()
                NavigationEvent.RELOAD -> reload()
                NavigationEvent.STOP_LOADING -> stopLoading()
            }
        }
    }

    /**
     * True when the web view is able to navigate backwards, false otherwise.
     */
    var canGoBack: Boolean by mutableStateOf(false)
        internal set

    /**
     * True when the web view is able to navigate forwards, false otherwise.
     */
    var canGoForward: Boolean by mutableStateOf(false)
        internal set

    /**
     * Navigates the webview back to the previous page.
     */
    fun navigateBack() {
        coroutineScope.launch { navigationEvents.emit(NavigationEvent.BACK) }
    }

    /**
     * Navigates the webview forward after going back from a page.
     */
    fun navigateForward() {
        coroutineScope.launch { navigationEvents.emit(NavigationEvent.FORWARD) }
    }

    /**
     * Reloads the current page in the webview.
     */
    fun reload() {
        coroutineScope.launch { navigationEvents.emit(NavigationEvent.RELOAD) }
    }

    /**
     * Stops the current page load (if one is loading).
     */
    fun stopLoading() {
        coroutineScope.launch { navigationEvents.emit(NavigationEvent.STOP_LOADING) }
    }
}


/**
 * A wrapper class to hold errors from the WebView.
 */
@Immutable
actual data class WebViewError(
    /**
     * The request the error came from.
     */
    val request: WebResourceRequest?,
    /**
     * The error that was reported.
     */
    val error: WebResourceError
)

/**
 * Creates a WebView state that is remembered across Compositions.
 *
 * @param url The url to load in the WebView
 * @param additionalHttpHeaders Optional, additional HTTP headers that are passed to [WebView.loadUrl].
 *                              Note that these headers are used for all subsequent requests of the WebView.
 */
@Composable
actual fun rememberWebViewState(
    url: String,
    additionalHttpHeaders: Map<String, String>
): WebViewState =
// Rather than using .apply {} here we will recreate the state, this prevents
    // a recomposition loop when the webview updates the url itself.
    remember(url, additionalHttpHeaders) {
        WebViewState(
            WebContent.Url(
                url = url,
                additionalHttpHeaders = additionalHttpHeaders
            )
        )
    }

/**
 * Creates a WebView state that is remembered across Compositions.
 *
 * @param data The uri to load in the WebView
 */
@Composable
actual fun rememberWebViewStateWithHTMLData(data: String, baseUrl: String?): WebViewState =
    remember(data, baseUrl) {
        WebViewState(WebContent.Data(data, baseUrl))
    }