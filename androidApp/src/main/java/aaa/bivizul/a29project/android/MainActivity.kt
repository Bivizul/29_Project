package aaa.bivizul.a29project.android

import aaa.bivizul.a29project.data.spbkkutil.checkSpbknet
import aaa.bivizul.a29project.data.spbkkutil.getSpbkdlg
import aaa.bivizul.a29project.ui.root.Rootspbk
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

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

}
