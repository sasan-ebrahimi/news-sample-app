package com.s_ebrahimi.newssample.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SearchView

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.s_ebrahimi.newssample.R
import com.s_ebrahimi.newssample.adapters.ArticleClickListener
import com.s_ebrahimi.newssample.viewmodel.ArticleListViewModel
import com.s_ebrahimi.newssample.adapters.ArticleRecyclerAdapter
import com.s_ebrahimi.newssample.model.RequestParams
import com.s_ebrahimi.newssample.uicomponents.ErrorView
import com.s_ebrahimi.newssample.viewmodel.ArticleListViewModelFactory
import com.s_ebrahimi.newssample.uicomponents.FilterView
import com.s_ebrahimi.newssample.util.Constants

/**
 * Main activity of application which shows list of articles
 * this activity implements several interfaces since it handles following actions :
 * search articles, filter articles based on category and language, refresh list, clicking on article item and connection error handling
 *
 */
class ArticleListActivity : BaseActivity(),
    ArticleClickListener,
    FilterView.OnFilterListener,
    SwipeRefreshLayout.OnRefreshListener,
    ErrorView.OnRetryListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleRecyclerAdapter
    private lateinit var articleListViewModel: ArticleListViewModel
    private lateinit var searchView: SearchView
    private lateinit var requestParams: RequestParams
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var ivFilter: ImageView
    private lateinit var filterView: FilterView
    private lateinit var errorView: ErrorView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)
        initialize()
        initViewModel()
    }

    /**
     *  Initializes view model and recycler view adapter
     */
    fun initViewModel() {
        if (isOnline()) {
            errorView.visibility = View.GONE
            val viewModelFactory = ArticleListViewModelFactory(requestParams)
            articleListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ArticleListViewModel::class.java)
            adapter = ArticleRecyclerAdapter(this)
            recyclerView.adapter = adapter;
            initObservers()
        } else {
            errorView.visibility = View.VISIBLE
            showSnackbar(swipeRefreshLayout, "Internet Connection Error ...")
        }

    }

    /**
     * Calls view model method to fetch new data
     * This method is fired when new request parameters are chosen by user ( filter or search )
     */
    private fun fetchNewData() {
        if (isOnline()) {
            articleListViewModel.fetchNewData(requestParams)
        } else {
            swipeRefreshLayout.isRefreshing = false
            showSnackbar(swipeRefreshLayout, "Internet Connection Error ...")
        }
    }

    /**
     * Initializes view model observers
     */
    private fun initObservers() {
        articleListViewModel.articleLiveData?.observe(this, Observer { pagedList ->
            adapter!!.submitList(pagedList);
            swipeRefreshLayout.isRefreshing = false
        })
        articleListViewModel.requestState?.observe(this, Observer { requestState ->
            adapter?.setRequestState(requestState)
            swipeRefreshLayout.isRefreshing = false
        })
    }

    /**
     * initializes views
     */
    private fun initialize() {

        errorView = findViewById(R.id.error_view)
        errorView.setOnRetryListener(this)

        recyclerView = findViewById(R.id.article_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        requestParams = RequestParams()

        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)

        swipeRefreshLayout = findViewById(R.id.swipeContainer)
        swipeRefreshLayout.setOnRefreshListener(this)

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet) as LinearLayout);

        ivFilter = findViewById(R.id.iv_filter)
        ivFilter.setOnClickListener(View.OnClickListener {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        })

        filterView = findViewById(R.id.filter_view)
        filterView.setOnFilterApplyListener(this)

        (findViewById(R.id.iv_info) as ImageView).setOnClickListener(View.OnClickListener { showToastLong(Constants.API_SOURCE_MESSAGE) })
    }

    /**
     * OnFilterListener function which is fired when user clicks apply button on filter view
     * @param language : selected language to filter
     * @param category : selected category to filter
     */
    override fun OnFilterApplied(language: String, category: String) {
        if (requestParams.language.equals(language) || requestParams.category.equals(category)) {
            requestParams.language = language
            requestParams.category = category
            fetchNewData()
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    /**
     * This is fired when clicking on an item of article recycler view
     * @param position : position of clicked item
     */
    override fun onArticleClicked(position: Int) {
        val item = adapter?.getItem(position)
        val intent = Intent(this, ArticleDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("articles", item)
        intent.putExtra("myBundle", bundle)
        startActivity(intent)
    }

    /**
     * This is fired when recycler view is pulled down to be refreshed and get data from scratch
     */
    override fun onRefresh() {
        fetchNewData()
    }

    /**
     * When internet connection is off, an error is showed on screen to retry
     * This function is called when refresh button is pressed to try to reconnect
     */
    override fun OnRetryClick() {
        initViewModel()
    }

    /**
     * SearchView interface function
     * This is called when search is applied on SearchView and calls for new data with this new query
     * @param query : entered text by user
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!requestParams.query.equals(query.toString())){
            requestParams.query = query.toString()
            fetchNewData()
        }
        return false
    }

    /**
     * SearchView interface function
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    /**
     * SearchView interface function
     * This is called when search view is closed
     */
    override fun onClose(): Boolean {
        if (this.requestParams.query.trim() != "") {
            this.requestParams.query = ""
            fetchNewData()
        }
        return false
    }

    /**
     * If filter view is opened, closes it, otherwise calls parent function
     */
    override fun onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed()
        }
    }
}
