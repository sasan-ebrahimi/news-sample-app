package com.s_ebrahimi.newssample.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.s_ebrahimi.newssample.R
import com.s_ebrahimi.newssample.databinding.ActivityArticleDetailBinding
import com.s_ebrahimi.newssample.model.Article
import com.s_ebrahimi.newssample.viewmodel.ArticleDetailViewModel
import com.s_ebrahimi.newssample.viewmodel.ArticleDetailViewModelFactory

/**
 * Activity to show details related to an article
 * an Article object is needed to be passed through intent
 */
class ArticleDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        val article = extractArticle()
        initDataBinding(article)

    }

    /**
     * Extracts passed Article object to this Activity
     * @return passed Article object
     */
    private fun extractArticle(): Article {
        val bundle = intent.getBundleExtra("myBundle")
        var article = bundle.getParcelable<Article>("articles") as Article
        return article
    }

    /**
     * Initializes databinding
     * @param article : the Article object to be binded to view
     */
    private fun initDataBinding(article: Article){
        val viewModelFactory = ArticleDetailViewModelFactory(article)
        val articleDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ArticleDetailViewModel::class.java)

        DataBindingUtil.setContentView<ActivityArticleDetailBinding>(
            this, R.layout.activity_article_detail
        ).apply {
            this.setLifecycleOwner(this@ArticleDetailActivity)
            this.viewmodel = articleDetailViewModel
        }
    }

}