package com.s_ebrahimi.newssample.uicomponents

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.s_ebrahimi.newssample.R

/**
 * An extension of LinearLayout to show a simple error message with icon and retry button
 */
class ErrorView : LinearLayout {

    /**
     * Interface to listen to retry button click
     */
    interface OnRetryListener {
        fun OnRetryClick()
    }
    private lateinit var btnRetry : Button
    private lateinit var tvError : TextView
    private var listener : OnRetryListener? = null


    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context)
    }

    /**
     * Initialize views and layout
     */
    private fun init(context: Context) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_error_view, this, true)

        tvError = findViewById(R.id.tv_error)
        btnRetry = findViewById(R.id.btn_retry)

        btnRetry.setOnClickListener(View.OnClickListener {
            if(listener != null){
                listener!!.OnRetryClick()
            }
        })
    }

    /**
     * Set retry button listener
     * @param listener : retry button click listener
     */
    fun setOnRetryListener(listener: OnRetryListener){
        this.listener = listener
    }

    /**
     * Sets error message text
     * @param title : error text to be set on textview
     */
    fun setTitle(title : String){
        tvError.text = title
    }

    /**
     * Hides retry button
     */
    fun hideButton(){
        btnRetry.visibility = View.GONE
    }

    /**
     * shows retry button
     */
    fun showButton(){
        btnRetry.visibility = View.VISIBLE
    }

}
