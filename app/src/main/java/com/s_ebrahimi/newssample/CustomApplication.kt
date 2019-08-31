package com.s_ebrahimi.newssample

import android.app.Application
import com.s_ebrahimi.newssample.util.OkHttpClientGenerator
import okhttp3.OkHttpClient

/**
 * Extension of Application to initialize an http client from .cert file at the entry point of app to prevent http communication errors
 */
class CustomApplication : Application() {

    companion object {
        var okHttpClient : OkHttpClient? = null
    }

    override fun onCreate() {
        super.onCreate()
        okHttpClient = OkHttpClientGenerator.generateClient(resources.openRawResource(R.raw.newsapi))
    }
}