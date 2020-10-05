package br.com.brb.savemovies.util

import android.content.Context
import android.net.ConnectivityManager


class VerifyInternet {

    fun verifyConnection(context: Context): Boolean {
        var connected = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

         connectivityManager.activeNetworkInfo?.let{
             connected = it.isAvailable && it.isConnected
        }

        return connected
    }
}
