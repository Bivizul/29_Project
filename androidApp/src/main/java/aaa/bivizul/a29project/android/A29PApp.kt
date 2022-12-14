package aaa.bivizul.a29project.android

import aaa.bivizul.a29project.data.spbkkutil.Spbkcon.SPBKOSAI
import android.app.Application
import com.onesignal.OneSignal

class A29PApp : Application() {

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(SPBKOSAI)
    }

}