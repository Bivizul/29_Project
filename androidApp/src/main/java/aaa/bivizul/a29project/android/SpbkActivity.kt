package aaa.bivizul.a29project.android

import aaa.bivizul.a29project.data.spbkkutil.Spbkcon.SPBKDOR
import aaa.bivizul.a29project.data.spbkkutil.Spbkcon.SPBKKOR
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import im.delight.android.webview.AdvancedWebView

@Suppress("DEPRECATION")
class SpbkActivity : ComponentActivity(), AdvancedWebView.Listener {

    lateinit var spbkwv: AdvancedWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spbk)

        val url = intent.getStringExtra(SPBKKOR) ?: SPBKDOR

        spbkwv = findViewById<View>(R.id.spbkadvwv) as AdvancedWebView

        spbkwv.webViewClient = WebViewClient()
        spbkwv.webChromeClient = MyChromeClient()

        spbkwv.setListener(this, this)
        spbkwv.setMixedContentAllowed(false)

        setSettings()

        if (savedInstanceState == null) {
            spbkwv.post {
                kotlin.run { spbkwv.loadUrl(url) }
            }
        }

        spbkwv.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.action == MotionEvent.ACTION_UP &&
                spbkwv.canGoBack()
            ) {
                spbkwv.goBack()
                return@OnKeyListener true
            }
            false
        })

    }

    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        spbkwv.onResume()
    }

    @SuppressLint("NewApi")
    override fun onPause() {
        spbkwv.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        spbkwv.onDestroy()
        super.onDestroy()
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        spbkwv.onActivityResult(requestCode, resultCode, intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!spbkwv.onBackPressed()) {
            return
        }
        finishAndRemoveTask()
        System.exit(0)
    }


    override fun onPageStarted(url: String?, favicon: Bitmap?) {}

    override fun onPageFinished(url: String?) {}

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {}

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?,
    ) {
    }

    override fun onExternalPageRequest(url: String?) {}

    @SuppressLint("SetJavaScriptEnabled")
    private fun setSettings() {
        val spbkws = spbkwv.settings
        spbkws.javaScriptEnabled = true
        spbkws.loadWithOverviewMode = true
        spbkws.allowFileAccess = true
        spbkws.domStorageEnabled = true
        spbkws.builtInZoomControls = true
        spbkws.displayZoomControls = false
        spbkws.useWideViewPort = true
        spbkws.setSupportZoom(true)
        spbkws.setCacheMode(WebSettings.LOAD_NO_CACHE)
        spbkws.userAgentString = spbkws.userAgentString.replace("; wv", "")
    }

    var filePathCallback: ValueCallback<Array<Uri>>? = null
    private val REQUEST_CODE = 100

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        spbkwv.saveState(outState)
    }


    inner class MyChromeClient : WebChromeClient() {

        override fun onShowFileChooser(
            view: WebView,
            filePath: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams,
        ): Boolean {
            filePathCallback = filePath
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
            startActivityForResult(intent, REQUEST_CODE)
            return true
        }


        private var spbkCustomView: View? = null
        private var spbkCustomViewCallback: CustomViewCallback? = null
        private var spbkOriginalOrientation = 0
        private var spbkOriginalSystemUiVisibility = 0

        override fun getDefaultVideoPoster(): Bitmap? {
            return if (spbkCustomView == null) {
                null
            } else BitmapFactory.decodeResource(
                this@SpbkActivity.applicationContext.resources,
                2130837573
            )
        }

        override fun onHideCustomView() {
            (this@SpbkActivity.window.decorView as FrameLayout).removeView(spbkCustomView)
            spbkCustomView = null
            this@SpbkActivity.window.decorView.systemUiVisibility = spbkOriginalSystemUiVisibility
            this@SpbkActivity.requestedOrientation = spbkOriginalOrientation
            spbkCustomViewCallback!!.onCustomViewHidden()
            spbkCustomViewCallback = null
        }

        override fun onShowCustomView(
            paramView: View?,
            paramCustomViewCallback: CustomViewCallback?,
        ) {
            if (spbkCustomView != null) {
                onHideCustomView()
                return
            }
            spbkCustomView = paramView
            spbkOriginalSystemUiVisibility = this@SpbkActivity.window.decorView.systemUiVisibility
            spbkOriginalOrientation = this@SpbkActivity.requestedOrientation
            spbkCustomViewCallback = paramCustomViewCallback
            (this@SpbkActivity.window.decorView as FrameLayout).addView(
                spbkCustomView,
                FrameLayout.LayoutParams(-1, -1)
            )
            this@SpbkActivity.window.decorView.systemUiVisibility =
                3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

}