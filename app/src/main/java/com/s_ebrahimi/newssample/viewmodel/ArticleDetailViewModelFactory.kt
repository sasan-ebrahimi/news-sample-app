package com.s_ebrahimi.newssample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s_ebrahimi.newssample.model.Article

/**
 * ArticleDetailViewModel factory
 * This is used to pass Article object to the ViewModel
 * The default constructor takes an Article object and passes it to ViewModel create function
 */
class ArticleDetailViewModelFactory(private val article: Article) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticleDetailViewModel(article) as T
    }

}