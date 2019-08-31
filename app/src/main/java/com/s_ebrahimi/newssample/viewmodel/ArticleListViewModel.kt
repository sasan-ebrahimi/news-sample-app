package com.s_ebrahimi.newssample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.s_ebrahimi.newssample.model.Article
import com.s_ebrahimi.newssample.model.RequestParams
import com.s_ebrahimi.newssample.repository.ArticleDataFactory
import com.s_ebrahimi.newssample.model.RequestState
import com.s_ebrahimi.newssample.util.Constants
import java.util.concurrent.Executors

/**
 * ArticleListActivity ViewModel
 * This class has two main LiveData object :
 * 1. A LiveData of PagedList<Article> which holds list of Articles
 * 2. A LiveData of RequestState which keeps http request state
 * Also it has an ArticleDataFactory object which is used to pass new RequestParams to DataSource
 */
class ArticleListViewModel : ViewModel {


    var requestState: LiveData<RequestState>? = null
    var articleLiveData: LiveData<PagedList<Article>>? = null
    lateinit var articleDataFactory: ArticleDataFactory

    constructor(requestParams: RequestParams) {
        init(requestParams)
    }

    /**
     * Restarts fetching data using new RequestParams
     * @param requestParams : The new RequestParams object to be used to fetch data
     */
    fun fetchNewData(requestParams: RequestParams) {
        this.articleDataFactory.setRequestParams(requestParams)
    }

    /**
     * initializes variables
     * @param requestParams : RequestParams object for fetching data from DataSource
     */
    private fun init(requestParams: RequestParams) {

        articleDataFactory = ArticleDataFactory(requestParams)
        requestState = Transformations.switchMap(
            articleDataFactory.getLiveDataSource()
        ) { dataSource -> dataSource.getLiveRequestState() }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(1)
            .setPageSize(Constants.PAGE_SIZE).build()

        var executor = Executors.newFixedThreadPool(5)
        articleLiveData = LivePagedListBuilder(articleDataFactory, pagedListConfig)
            .setFetchExecutor(executor!!)
            .build()
    }
}