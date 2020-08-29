package com.jeff.gitusers.main.view

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.gitusers.BuildConfig
import com.jeff.gitusers.R
import com.jeff.gitusers.adapter.UserListAdapter
import com.jeff.gitusers.android.base.extension.invokeSimpleDialog
import com.jeff.gitusers.android.base.extension.longToast
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.databinding.ActivityMainBinding
import com.jeff.gitusers.main.presenter.MainPresenter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.view.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {
    private lateinit var adapter: UserListAdapter
    private lateinit var progressDialog: ProgressDialog

    private lateinit var mainBinding : ActivityMainBinding

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
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                //if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        //bottom of list!
                        Timber.d("==q Loading.. more users")
                        mainPresenter.loadMoreUsers(adapter.getLastIndexId())
                        //isLoading = true
                    }
                //}
            }
        })
    }


    private fun setUpToolbarTitle() {
        setSupportActionBar(mainBinding.toolbar)

        //supportActionBar!!.title = getString(R.string.app_name)
        supportActionBar!!.title = resources.getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about -> {
                invokeSimpleDialog(
                    getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME,
                    getString(R.string.about_description)
                            + "\nDeveloped by : Jeff Arce"
                )
            }
        }

        return super.onOptionsItemSelected(item)
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
