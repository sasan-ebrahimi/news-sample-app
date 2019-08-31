package com.s_ebrahimi.newssample.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * A helper class to check internet connection
 */
class InternetConnectionHelper {

    companion object {

        /**
         * Checks if the phone has internet connection
         * @param context : Context
         * @return true if connection availabe, false otherwise
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}
