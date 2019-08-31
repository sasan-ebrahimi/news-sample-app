package com.s_ebrahimi.newssample.network

import com.s_ebrahimi.newssample.CustomApplication
import com.s_ebrahimi.newssample.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * this class provides a singleton object of retrofit
 * which can be used to communicate with api
 */
object RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private var BASE_URL = Constants.BASE_URL

    val retrofitInstance: Retrofit?
        get(){
            if(retrofit == null){

                var okHttpClient = CustomApplication.okHttpClient

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }

}