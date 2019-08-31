package com.s_ebrahimi.newssample.uicomponents

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import com.s_ebrahimi.newssample.R

/**
 * An extension of LinearLayout to show a filter view
 * Since this app is demo this class uses static values to filter
 * There are two field of filtering : language and category
 */
class FilterView : LinearLayout {

    /**
     * An interface that can be observed for applying filters
     */
    interface OnFilterListener {

        /**
         * Fires when apply button clicked
         * @param language : selected language
         * @param category : selected category, if category is not selected the value is an empty string
         */
        fun OnFilterApplied(language : String, category : String)
    }

    private lateinit var btnApply : Button
    private lateinit var btnClearCategory : Button
    private var listener : OnFilterListener? = null
    private lateinit var groupLanguage : SingleSelectToggleGroup
    private lateinit var groupCategory : SingleSelectToggleGroup
    private var language = ""
    private var category = ""


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
        inflater.inflate(R.layout.layout_filter_view, this, true)

        setOnClickListener(OnClickListener {
        })

        btnApply = findViewById(R.id.btn_apply)
        btnClearCategory = findViewById(R.id.btn_clear_category)
        groupLanguage = findViewById(R.id.group_language)
        groupCategory = findViewById(R.id.group_category)


        btnApply.setOnClickListener(View.OnClickListener {
            if(listener != null){
                listener!!.OnFilterApplied(language,category)
            }
        })

        btnClearCategory.setOnClickListener(View.OnClickListener {
            groupCategory.clearCheck()
            category = ""
        })
        groupLanguage.setOnCheckedChangeListener(object :SingleSelectToggleGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int) {
                language = getLanguage(checkedId)
            }
        })

        groupCategory.setOnCheckedChangeListener(object :SingleSelectToggleGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int) {
                category = getCategory(checkedId)
            }
        })



    }

    /**
     * sets OnFilterListener
     * @param listener : listener to be set
     */
    fun setOnFilterApplyListener(listener: OnFilterListener){
        this.listener = listener
    }

    /**
     * Returns a value for selected language view
     * @param id : selected view id
     */
    private fun getLanguage(id : Int) : String{
        when(id){
            R.id.choice_en -> return "en"
            R.id.choice_nl -> return "nl"
            R.id.choice_fr -> return "fr"
            R.id.choice_ar -> return "ar"
            else -> return "en"
        }
    }

    /**
     * Returns a value for selected category view
     * @param id : selected view id
     */
    private fun getCategory(id : Int) : String{
        when(id){
            R.id.choice_business -> return "business"
            R.id.choice_entertainment -> return "entertainment"
            R.id.choice_general -> return "general"
            R.id.choice_health -> return "health"
            R.id.choice_science -> return "science"
            R.id.choice_technology -> return "technology"
            R.id.choice_sports -> return "sports"
            else -> return ""
        }
    }

}
