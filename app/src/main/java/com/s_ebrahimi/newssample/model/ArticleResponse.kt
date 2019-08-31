package com.s_ebrahimi.newssample.model

import com.google.gson.annotations.SerializedName

/**
 * Model for api response
 * for successful requests the response contains status, articles and totalResults
 * for unsuccessful requests the response contains status, code, and message
 */
data class ArticleResponse(
    @SerializedName("articles") var articles: MutableList<Article>,
    @SerializedName("status") var status: String,
    @SerializedName("totalResults") var totalResults: Long,
    @SerializedName("code") var errorCode: String?,
    @SerializedName("message") var errorMessage: String?
) {

    companion object {
        val STATUS_OK = "ok"
        val STATUS_ERROR = "error"
    }

    override fun toString(): String {
        return "ArticleResponse(articles=${articles.size}, status='$status', totalResults=$totalResults)"
    }
}