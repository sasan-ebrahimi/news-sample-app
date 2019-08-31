package com.s_ebrahimi.newssample.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.s_ebrahimi.newssample.R

/**
 * ViewHolder class for ArticleRecycler adapter last item ( footer )
 */
class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvMessage: TextView

    init {
        tvMessage = itemView.findViewById(R.id.tv_message)
    }
}
