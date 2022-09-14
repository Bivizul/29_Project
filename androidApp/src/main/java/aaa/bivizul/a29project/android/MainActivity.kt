package aaa.bivizul.a29project.android

import aaa.bivizul.a29project.data.spbkkutil.checkSpbknet
import aaa.bivizul.a29project.data.spbkkutil.getSpbkdlg
import aaa.bivizul.a29project.ui.root.Rootspbk
import aaa.bivizul.a29project.ui.web.AccompanistWebChromeClient
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100
//    val a = AccompanistWebChromeClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (checkSpbknet(this)) {
                Rootspbk()
            } else {
                getSpbkdlg(this, this)
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && data?.getData() != null) {
//            if (requestCode == REQUEST_CODE) {
//
//                AccompanistWebChromeClient().getFilePath().onReceiveValue(
//                    WebChromeClient.FileChooserParams.parseResult(resultCode, intent)
//                )
//            }
//        }
//    }
}
