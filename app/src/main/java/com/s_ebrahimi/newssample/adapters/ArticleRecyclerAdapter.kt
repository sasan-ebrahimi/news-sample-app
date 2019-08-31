package com.s_ebrahimi.newssample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s_ebrahimi.newssample.R
import com.s_ebrahimi.newssample.model.Article
import com.s_ebrahimi.newssample.model.RequestState
import com.s_ebrahimi.newssample.util.ImageLoaderHelper

/**
 * RecyclerView adapter for articles
 * It has an item at the end of list to show status of fetching data and is set based on RequestState object
 */
class ArticleRecyclerAdapter(listener: ArticleClickListener) :
    PagedListAdapter<Article, RecyclerView.ViewHolder>(Article.DIFF_CALLBACK) {

    private val articleClickListener: ArticleClickListener
    private var requestState: RequestState? = null

    init {
        articleClickListener = listener
    }

    companion object {
        /**
         * This adapters shows four types of view :
         * two for main items,
         * and two for footer to show fetching data status in the end of list
         */
        private val VIEW_TYPE_ITEM_LARGE = 1
        private val VIEW_TYPE_ITEM_SMALL = 2
        private val VIEW_TYPE_FOOTER_LOADING = 3
        private val VIEW_TYPE_FOOTER_MESSAGE = 4
    }


    /**
     * Based on viewType, return appropriate ViewHolder
     */
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        if (viewType == VIEW_TYPE_FOOTER_LOADING) {
            val view =
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_articles_loading, parent, false)
            return LoadingViewHolder(view)
        } else if (viewType == VIEW_TYPE_FOOTER_MESSAGE) {
            val view =
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_articles_error, parent, false)
            return ErrorViewHolder(view)
        } else if (viewType == VIEW_TYPE_ITEM_LARGE) {
            val view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_articles_large, parent, false)
            return ArticleRecyclerViewHolder(view, articleClickListener)
        } else {
            val view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_article_small, parent, false)
            return ArticleRecyclerViewHolder(view, articleClickListener)
        }


    }

    /**
     * Bind data for ViewHolders which need to be bind ( Loading ViewHolder doesn't need data binding )
     */
    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ErrorViewHolder) {
            holder.tvMessage.text =
                if (requestState?.errorMessage != null && requestState?.errorMessage!!.trim() != "") requestState?.errorMessage else ""
        } else if (holder is ArticleRecyclerViewHolder) {
            val item = getItem(position)
            holder.tvTitle.text = item?.title
            holder.tvSource.text = if (item?.source != null && item.source.name != null) item.source.name else "unknown"
            ImageLoaderHelper.loadUrlToImageView(item?.imageUrl, holder.imageView)
        }
    }

    /**
     * Based on position return appropriate view type
     * Last index is the footer which shows fetching data status : returns VIEW_TYPE_FOOTER_LOADING or VIEW_TYPE_FOOTER_MESSAGE
     * For non last position returns VIEW_TYPE_ITEM_LARGE when position % 5 == 0, otherwise returns VIEW_TYPE_ITEM_SMALL
     */
    override fun getItemViewType(position: Int): Int {

        if (position == itemCount - 1) {
            if (requestState == null || requestState?.state == RequestState.STATE_LOADING)
                return VIEW_TYPE_FOOTER_LOADING
            else
                return VIEW_TYPE_FOOTER_MESSAGE
        } else if (position % 5 == 0) {
            return VIEW_TYPE_ITEM_LARGE
        } else {
            return VIEW_TYPE_ITEM_SMALL
        }
    }

    /**
     * Sets RequestState object and notifies the adapter that last item has been changed
     * @param requestState : new RequestState object to be set in this class
     */
    fun setRequestState(requestState: RequestState?) {
        this.requestState = requestState
        notifyItemChanged(getItemCount() - 1)
    }

    /**
     * @return item count plus one so that a status item in the end can be showed
     */
    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    public override fun getItem(position: Int): Article? {
        return super.getItem(position)
    }
}



