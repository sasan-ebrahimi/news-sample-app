package com.s_ebrahimi.newssample.adapters

/**
 * interface for actions on Article RecyclerView items
 */
interface ArticleClickListener {

    /**
     * fires when an article item is clicked
     * @param position : Position of clicked item
     */
    fun onArticleClicked(position: Int)
}
