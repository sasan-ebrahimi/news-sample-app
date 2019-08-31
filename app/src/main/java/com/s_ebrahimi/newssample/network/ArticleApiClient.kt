package com.s_ebrahimi.newssample.network

import com.google.gson.GsonBuilder
import com.s_ebrahimi.newssample.model.ArticleResponse
import com.s_ebrahimi.newssample.model.RequestParams
import com.s_ebrahimi.newssample.model.RequestState
import com.s_ebrahimi.newssample.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This class is responsible for making http request to get Articles list
 * Passing a RequestParams object through constructor is required to send parameters to API
 */
class ArticleApiClient {

    /**
     * Params to be sent through http request
     */
    var requestParams: RequestParams

    constructor(requestParams: RequestParams) {
        this.requestParams = requestParams
    }

    /**
     * Commits an http request to get a list ( one page ) of articles
     * @param page : Page number to be fetched
     * @param pageSize : Size of page that is going to be fetched ( number of articles )
     * @param articleFetchListener : A listener to be fired when the request is done
     */
    fun fetch(page: Long, pageSize: Int ,articleFetchListener: ArticleFetchListener) {

        val call = getCall(page, pageSize)
        call?.enqueue(object : Callback<ArticleResponse> {

            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful()) {
                    val status = response.body()?.status
                    if (status == ArticleResponse.STATUS_OK) {
                        articleFetchListener.OnArticleFetched(response.body()!!.articles , (response.body() as ArticleResponse).totalResults)
                    } else if (status == ArticleResponse.STATUS_ERROR) {
                        handleUnSuccesfulResponse(response,articleFetchListener)
                    } else {
                        handleUnSuccesfulResponse(response,articleFetchListener)
                    }
                } else {
                    handleUnSuccesfulResponse(response,articleFetchListener)
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                requestFailed(t , articleFetchListener)
            }
        })
    }


    /**
     * This method handles error when the http request connection fails
     * @param throwable : Passed Throwable object from retrofit
     * @param articleFetchListener : The listener of request, to be notified of failure
     */
    private fun requestFailed(throwable: Throwable, articleFetchListener: ArticleFetchListener) {
        val errorMessage = if (throwable == null) RequestState.MESSAGE_UNKNOWN_ERROR else throwable.message
        val requestState = RequestState()
        requestState.setFailureState(RequestState.ERROR_TYPE_CONNECTION, null, errorMessage)
        articleFetchListener.OnFailure(requestState)
    }

    /**
     * This method handles error when the http request fails
     * @param response : Passed response object from retrofit
     * @param articleFetchListener : The listener of request, to be notified of failure
     */
    private fun handleUnSuccesfulResponse(response: Response<ArticleResponse>, articleFetchListener: ArticleFetchListener) {
        var code = response.code()
        var requestState =
            RequestState(RequestState.STATE_FAILURE)
        if (code in 400..499) {
            val gson = GsonBuilder().create()
            val errorResponse = gson.fromJson(response.errorBody()!!.string(), ArticleResponse::class.java)
            requestState.setFailureState(
                RequestState.ERROR_TYPE_BAD_REQUEST,
                errorResponse.errorCode,
                errorResponse.errorMessage
            )
            articleFetchListener.OnFailure(requestState)
        } else if (response.errorBody() != null) {
            val gson = GsonBuilder().create()
            val errorResponse = gson.fromJson(response.errorBody()!!.string(), ArticleResponse::class.java)
            requestState.setFailureState(
                RequestState.ERROR_TYPE_UNKNOWN,
                errorResponse.errorCode,
                errorResponse.errorMessage
            )
            articleFetchListener.OnFailure(requestState)
        } else {
            requestState.setFailureState(
                RequestState.ERROR_TYPE_UNKNOWN,
                null,
                RequestState.MESSAGE_UNKNOWN_ERROR
            )
            articleFetchListener.OnFailure(requestState)
        }
    }


    /**
     * Instanciates a Call object to be used for fetching data
     *
     * @param page : Page number to be fetched
     * @param requestedLoadSize : Size of page that is going to be fetched ( number of articles )
     * @return Call object of ArticleResponse
     */
    private fun getCall(page: Long, requestedLoadSize: Int): Call<ArticleResponse> {
        val service = RetrofitClientInstance.retrofitInstance?.create(GetArticlesService::class.java)
        val call = service?.getArticles(
            Constants.API_KEY,
            page,
            requestedLoadSize,
            requestParams.country,
            requestParams.query,
            requestParams.category,
            requestParams.language
        )
        return call!!
    }

}