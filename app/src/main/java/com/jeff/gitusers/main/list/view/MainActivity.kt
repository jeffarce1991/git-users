package com.jeff.gitusers.main.list.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.gitusers.BuildConfig
import com.jeff.gitusers.R
import com.jeff.gitusers.adapter.UserListAdapter
import com.jeff.gitusers.android.base.extension.invokeSimpleDialog
import com.jeff.gitusers.android.base.extension.longToast
import com.jeff.gitusers.android.base.extension.shortToast
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.databinding.ActivityMainBinding
import com.jeff.gitusers.main.list.presenter.MainPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainActivity : MvpActivity<MainView, MainPresenter>(),
    MainView {
    private lateinit var adapter: UserListAdapter
    private lateinit var progressDialog: ProgressDialog

    private lateinit var mainBinding : ActivityMainBinding

    //private var searchView: SearchView? = null
    lateinit var searchView: SearchView

    @Inject
    internal lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainPresenter.loadInitialUsers()

        setUpToolbarTitle()
        mainBinding.root.swipeRefreshLayout.setOnRefreshListener {
            //mainPresenter.loadInitialUsers()
        }
        initScrollListener()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        initializeSearchView(menu)

        return true
    }

    private fun setUpToolbarTitle() {
        setSupportActionBar(mainBinding.toolbar)

        //supportActionBar!!.title = getString(R.string.app_name)
        supportActionBar!!.title = resources.getString(R.string.app_name)
    }

    private fun initializeSearchView(menu: Menu?) {
        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        searchView =
            MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnCloseListener { true }

        val searchPlate =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchPlate.hint = resources.getString(R.string.search_hint)
        searchPlate.setHintTextColor(resources.getColor(R.color.light_gray))
        searchPlate.setTextColor(resources.getColor(R.color.white))

        val searchPlateView: View = searchView.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlateView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.transparent
            )
        )

    }

    override fun setSearchQueryListener(users: List<User>) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // do your logic here
                shortToast("Submitted $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!, users)
                return false
            }
        })
    }


    private fun filter(text: String,
                       users: List<User>) {
        //new array list that will hold the filtered data
        val filteredUsers: ArrayList<User> = ArrayList()

        //looping through existing elements
        for (user in users) {
            //if the existing elements contains the search input

            if (user.login.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))
                //|| user.notes.toLowerCase(Locale.getDefault()).contains(text.toLowerCase(Locale.getDefault()))
            ) {
                //adding the element to filtered list
                filteredUsers.add(user)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.update(filteredUsers)
    }


    private fun initScrollListener() {
        mainBinding.root.customRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                @NonNull recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(
                @NonNull recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                //if (!isLoading) {
                    if (layoutManager != null
                        && layoutManager.findLastCompletelyVisibleItemPosition()
                        == adapter.itemCount - 1
                        && adapter.itemCount > 29) {
                        //bottom of list!
                        Timber.d("==q Loading.. more users")
                        mainPresenter.loadMoreUsers(adapter.getLastIndexId())
                        //isLoading = true
                    }
                //}
            }
        })
    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateInitialUsers(users: List<User>) {
        adapter = UserListAdapter(this, users as MutableList<User>)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.root.customRecyclerView.layoutManager = layoutManager
        mainBinding.root.customRecyclerView.adapter = adapter

    }

    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateMoreUsers(users: List<User>) {
        adapter.addAll(users)
    }

    override fun createPresenter(): MainPresenter {
        return mainPresenter
    }

    override fun hideProgress() {
        mainBinding.root.swipeRefreshLayout.isRefreshing = false
    }
    override fun showProgress() {
        mainBinding.root.swipeRefreshLayout.isRefreshing = true
    }

    override fun showLoadingDataFailed() {
        longToast("Loading data failed")
        /*invokeSimpleDialog("Project420",
            "OK",
            "List is empty or null.")*/
    }

    override fun showToast(message: String) {
        longToast(message)
    }

    override fun showProgressRemote() {
        progressDialog = show(
            this,
            resources.getString(R.string.app_name),
            "Loading data remotely...")
    }

    override fun showProgressLocal() {
        progressDialog = show(
            this,
            resources.getString(R.string.app_name),
            "Loading data locally...")
    }
}
