package aaa.bivizul.a29project.data.spbkkutil

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import com.onesignal.OneSignal
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

actual fun getSpbkmm(): String {
    val manfacspbk = android.os.Build.MANUFACTURER
    val modelspbk = android.os.Build.MODEL
    return "$manfacspbk $modelspbk"
}

actual fun getSpbksim(spbkcon: Any): String {
    val context = spbkcon as Context
    val telmanspbk = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return telmanspbk.simCountryIso
}

actual fun getSpbkid(spbkcon: Any): String {
    val context = spbkcon as Context
    val sharedPreferences = context.getSharedPreferences("appprefspbk", Context.MODE_PRIVATE)
    var spbkid = sharedPreferences.getString("spbk_key", "nospbk") ?: "nospbk"
    if (spbkid == "nospbk") {
        val dateNow = Date()
        val simpleDateFormat = SimpleDateFormat("yyMMddhhmmssMs")
        val datetime = simpleDateFormat.format(dateNow)
        val randomNum = (10000 until 100000).random()
        spbkid = datetime + randomNum
        sharedPreferences.edit().putString("spbk_key", spbkid).apply()
    }
    return spbkid
}

actual fun getSpbkl(): String {
    return Locale.getDefault().language
}

actual fun getSpbkt(): String {
    val tzspbk = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)
    var zonespbk = "00:00"
    if (tzspbk.length > 3) {
        zonespbk = tzspbk.substring(3)
    }
    return zonespbk
}

actual fun getSpbkdlg(spbkcon: Any, spbkact: Any) {
    val context = spbkcon as Context
    val activity = spbkact as Activity
    AlertDialog.Builder(context).apply {
        setTitle("Oops, error connect with server")
        setMessage("Please exit the app and try again later")
        setPositiveButton("Exit") { _, _ ->
            activity.finish()
            System.exit(0)
        }
        setCancelable(true)
    }.create().show()
}

actual fun checkSpbknet(spbkcon: Any): Boolean {
    val context = spbkcon as Context
    val conmanspbk =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netinfspbk = conmanspbk.activeNetworkInfo
    return netinfspbk != null && netinfspbk.isConnected
}

actual fun sigSpbkoff() {
    OneSignal.disablePush(true)
}

actual fun screenSpbkexit(spbkact: Any) {
    val activity = spbkact as Activity
    activity.finish()
    System.exit(0)
}

actual fun spbkencod(s:String):String{
    val encodedUrl = URLEncoder.encode(s, StandardCharsets.UTF_8.toString())
    return encodedUrl
}

actual fun getWebActivity(spbkact: Any){
    val activity = spbkact as Activity
    val a = Class.forName("aaa.bivizul.a29project.android.WebActivity")
    val intent = Intent(activity,a)
    activity.startActivity(intent)
}