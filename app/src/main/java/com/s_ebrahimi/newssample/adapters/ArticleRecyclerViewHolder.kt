package com.s_ebrahimi.newssample.adapters

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.s_ebrahimi.newssample.R

/**
 * ViewHolder class for ArticleRecycler adapter main items
 */
class ArticleRecyclerViewHolder(@NonNull itemView: View, internal var articleClickListener: ArticleClickListener?) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener{

    internal var tvTitle: TextView
    internal var imageView: AppCompatImageView
    internal var shimmerFrameLayout: ShimmerFrameLayout
    internal var tvSource: TextView

    init {
        tvTitle = itemView.findViewById(R.id.tv_title)
        imageView = itemView.findViewById(R.id.image)
        shimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container)
        tvSource = itemView.findViewById(R.id.tv_source)
        itemView.setOnClickListener(this)
    }

    /**
     * When the itemView is clicked, fires articleClickListener.onArticleClicked
     */
    override fun onClick(v: View) {
        articleClickListener?.onArticleClicked(getAdapterPosition())
    }

}