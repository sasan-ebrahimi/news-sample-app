package com.s_ebrahimi.newssample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.s_ebrahimi.newssample.model.Article
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.s_ebrahimi.newssample.util.ImageLoaderHelper

/**
 * ArticleDetailActivity ViewModel
 * This class has a LiveData object of Article which can be observed
 */
class ArticleDetailViewModel constructor(article: Article) : ViewModel() {

    val article = MutableLiveData<Article>()

    init {
        this.article.value = article
    }

    companion object {

        /**
         * Handles article image url to be loaded on ImageView
         * It's useful to prevent null urls loading on ImageView
         * @param imageView : ImageView
         * @param url : article image url
         */
        @BindingAdapter("app:imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String?) {
            ImageLoaderHelper.loadUrlToImageView(url, imageView)
        }
    }


}