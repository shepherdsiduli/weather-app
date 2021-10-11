package za.co.shepherd.weatherapp.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun isNetworkAvailable(context: Context): Boolean {
     val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
     val activeNetworkInfo: NetworkInfo?
     activeNetworkInfo = connectivityManager.activeNetworkInfo
     return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}