package com.s_ebrahimi.newssample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s_ebrahimi.newssample.model.RequestParams

/**
 * ArticleListViewModel factory
 * This is used to pass RequestParams object to the ViewModel
 */
class ArticleListViewModelFactory(private val requestParams: RequestParams) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticleListViewModel(requestParams) as T
    }

}