package za.co.shepherd.weatherapp.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import za.co.shepherd.weatherapp.R
import za.co.shepherd.weatherapp.utils.DeviceUtils
import kotlin.system.exitProcess

fun isNetworkAvailable(context: Context): Boolean {
     val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
     val activeNetworkInfo: NetworkInfo?
     activeNetworkInfo = connectivityManager.activeNetworkInfo
     return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}

fun Activity.checkDeviceRoot(): Boolean {
     return if (DeviceUtils.isDeviceRooted(this)) {
          AlertDialog.Builder(this)
               .setMessage(R.string.device_rooted)
               .setCancelable(false)
               .setPositiveButton(R.string.ok) { _, _ ->
                    exitProcess(0)
               }.show()
          true
     } else {
          false
     }
}