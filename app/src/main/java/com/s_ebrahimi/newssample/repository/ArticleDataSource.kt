package com.s_ebrahimi.newssample.repository

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.s_ebrahimi.newssample.model.*
import com.s_ebrahimi.newssample.model.RequestParams
import com.s_ebrahimi.newssample.model.RequestState
import com.s_ebrahimi.newssample.network.ArticleApiClient
import com.s_ebrahimi.newssample.network.ArticleFetchListener

/**
 * Data source for articles
 * This class is responsible to provide observable data for viewmodel
 *
 * A MutableLiveData of RequestState notifies changes of request status
 */
class ArticleDataSource : PageKeyedDataSource<Long, Article> {

    private val liveRequestState: MutableLiveData<RequestState>
    private val articleApiClient: ArticleApiClient

    /**
     * initialization
     * @param requestParams : An instanse of RequestParams is required to pass to the ArticleApiClient constructor
     */
    constructor(requestParams: RequestParams) {
        liveRequestState = MutableLiveData<RequestState>()
        articleApiClient = ArticleApiClient(requestParams)
    }

    /**
     * @return live request state so that others can observe it if required
     */
    fun getLiveRequestState(): LiveData<RequestState> {
        return liveRequestState
    }

    /**
     * Sets request params of api client and validates the DataSource
     * which clears the DataSource and restarts lifecycle
     * @param requestParams : new request params
     */
    fun setRequestParams(requestParams: RequestParams) {
        articleApiClient.requestParams = requestParams
        invalidate()
    }

    /**
     * Provides the initial data ( first page ) and listens to the result
     * calls fetch method on api client
     */
    override fun loadInitial(
        @NonNull params: LoadInitialParams<Long>,
        @NonNull callback: LoadInitialCallback<Long, Article>
    ) {

        articleApiClient.fetch(1, params.requestedLoadSize, object : ArticleFetchListener {
            override fun OnFailure(requestState: RequestState) {
                liveRequestState.postValue(requestState)
            }

            override fun OnArticleFetched(list: MutableList<Article>, totalResults: Long) {
                callback.onResult(list, null, 1L)
                liveRequestState.postValue(RequestState(RequestState.STATE_SUCCESS))
            }
        })
    }

    override fun loadBefore(@NonNull params: LoadParams<Long>, @NonNull callback: LoadCallback<Long, Article>) {}


    /**
     * Provides a single page of data and listens to the result
     * calls fetch method on api client
     */
    override fun loadAfter(
        @NonNull params: LoadParams<Long>,
        @NonNull callback: LoadCallback<Long, Article>
    ) {

        articleApiClient.fetch(params.key, params.requestedLoadSize, object :
            ArticleFetchListener {
            override fun OnFailure(requestState: RequestState) {
                liveRequestState.postValue(requestState)
            }

            override fun OnArticleFetched(list: MutableList<Article>, totalResults: Long) {
                var nextKey =
                    (if (params.key * params.requestedLoadSize >= totalResults) null else params.key + 1)?.toLong()
                if (params.key == 1L)
                    list.removeAt(0)
                callback.onResult(list, nextKey)
                liveRequestState.postValue(RequestState(RequestState.STATE_SUCCESS))
                if (list.size < params.requestedLoadSize) {
                    liveRequestState.postValue(RequestState(RequestState.STATE_NO_MORE_DATA))
                } else {
                    liveRequestState.postValue(RequestState(RequestState.STATE_LOADING))
                }
            }
        })

    }

}
