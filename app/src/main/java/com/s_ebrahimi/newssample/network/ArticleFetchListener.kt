package com.s_ebrahimi.newssample.network

import com.s_ebrahimi.newssample.model.Article
import com.s_ebrahimi.newssample.model.RequestState

/**
 * An interface to be used when a request to server is committed
 */
interface ArticleFetchListener {

    /**
     * Fires when request is committed successfully
     * @param list : list of Article objects fetched due the request
     * @param totalResults : number of total results
     */
    fun OnArticleFetched(list: MutableList<Article> , totalResults : Long)

    /**
     * Fires when request is failed
     * @param requestState : a RequestState object that contains failure details
     */
    fun OnFailure(requestState: RequestState)

}