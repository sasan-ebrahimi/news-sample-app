package com.s_ebrahimi.newssample.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import androidx.recyclerview.widget.DiffUtil

/**
 * Model for article
 */
@Parcelize
data class Article(
    val source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    @SerializedName("urlToImage") var imageUrl: String?,
    @SerializedName("publishedAt") var date: String?,
    var content: String?,
    var isLoadingItem: Boolean = false
) : Parcelable {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Article> = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                val areSame =
                    oldItem.isLoadingItem === newItem.isLoadingItem && oldItem.source?.id.equals(newItem.source?.id) && oldItem.title?.equals(
                        newItem
                    )!!
                return areSame
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.equals(newItem)
                //return false
            }
        }
    }

    override fun equals(obj: Any?): Boolean {
        if (obj === this)
            return true

        val article = obj as Article?
        return article!!.source?.id.equals(this.source?.id) && article!!.title.equals(this.title)
    }

    /**
     * Reformats date to be shown on views
     * @return if date is null returns empty string, otherwise returns reformatted date string,
     */
    fun getDisplayDate(): String {
        if (date == null)
            return ""
        if (date!!.contains("T"))
            return date!!.subSequence(0, date!!.indexOf("T")).toString().replace('-','/')
        return date as String
    }

    /**
     * Reformats author to be shown on views
     * @return if author is null returns empty string, otherwise returns reformatted author string,
     */
    fun getDisplayAuthor():String{
        if(author== null)
            return " UNKNOWN "
        return author!!
    }

    override fun toString(): String {
        return "ArticleClickListener --> (author='$author', title='$title', description='$description', url='$url', imageUrl='$imageUrl', date='$date', content='$content')"
    }
}
