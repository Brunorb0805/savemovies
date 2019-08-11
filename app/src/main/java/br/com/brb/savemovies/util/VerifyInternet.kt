package br.com.brb.savemovies.util

import android.content.Context
import android.net.ConnectivityManager

class VerifyInternet {

    fun verifyConnection(context: Context): Boolean {
        val connected: Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connected = (connectivityManager.activeNetworkInfo != null
                && connectivityManager.activeNetworkInfo.isAvailable
                && connectivityManager.activeNetworkInfo.isConnected)
        return connected
    }
}