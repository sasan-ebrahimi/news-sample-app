package com.s_ebrahimi.newssample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.s_ebrahimi.newssample.model.Article
import com.s_ebrahimi.newssample.model.RequestParams

/**
 * DataSource factory for articles
 */
class ArticleDataFactory : DataSource.Factory<Long, Article> {

    private val articleDataSourceLD: MutableLiveData<ArticleDataSource>
    private var articleDataSource: ArticleDataSource? = null
    private var requestParams: RequestParams

    constructor(requestParams: RequestParams) {
        this.requestParams = requestParams
        this.articleDataSourceLD = MutableLiveData()
    }

    override fun create(): DataSource<Long, Article> {
        articleDataSource = ArticleDataSource(this.requestParams)
        articleDataSourceLD.postValue(articleDataSource)
        return articleDataSource as ArticleDataSource
    }

    /**
     * Sets article data source request params
     * @param requestParams : new request params to be set for data source
     */
    fun setRequestParams(requestParams: RequestParams){
        this.articleDataSource?.setRequestParams(requestParams)
    }

    /**
     * @return the data source live data
     */
    fun getLiveDataSource(): LiveData<ArticleDataSource> {
        return articleDataSourceLD
    }

}