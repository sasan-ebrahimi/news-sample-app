package com.s_ebrahimi.newssample.network

import com.s_ebrahimi.newssample.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * interface to be used in retrofit requests
 */
interface GetArticlesService {

    @GET("top-headlines")
    fun getArticles(
        @Query("apiKey") apiKey: String,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int,
        @Query("country") country: String,
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("language") language: String

    ): Call<ArticleResponse>

}