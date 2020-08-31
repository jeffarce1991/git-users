package com.jeff.gitusers.main.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.gitusers.R
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.databinding.ActivityDetailBinding
import com.jeff.gitusers.main.detail.presenter.UserDetailsPresenter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import kotlinx.android.synthetic.main.content_scrolling.view.*
import javax.inject.Inject

class UserDetailsActivity : MvpActivity<UserDetailsView, UserDetailsPresenter>(),
    UserDetailsView {

    companion object {
        private var EXTRA_ID = "EXTRA_ID"
        private var EXTRA_LOGIN = "EXTRA_LOGIN"
        private var EXTRA_AVATAR_URL = "EXTRA_AVATAR_URL"

        fun getStartIntent(
            context: Context,
            id : Int,
            login : String,
            avatarUrl : String


        ): Intent {
            return Intent(context, UserDetailsActivity::class.java)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_LOGIN, login)
                .putExtra(EXTRA_AVATAR_URL, avatarUrl)
        }
    }

    @Inject
    internal lateinit var userDetailsPresenter : UserDetailsPresenter

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setupToolbar()


        userDetailsPresenter.loadUserDetails(getUserName()!!)
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            userDetailsPresenter.loadUserDetails(getUserName()!!)
        }
    }

    private fun getUserName(): String? = intent.getStringExtra(EXTRA_LOGIN)

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.title = intent.getStringExtra(EXTRA_LOGIN)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        setHeaderImage()
    }

    private fun setHeaderImage() {
        Glide
            .with(this)
            .load(intent.getStringExtra(EXTRA_AVATAR_URL))
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.headerImage)
    }

    override fun createPresenter(): UserDetailsPresenter {
        return userDetailsPresenter
    }

    override fun setUserDetails(userDetails: UserDetails) {
        supportActionBar!!.title = userDetails.name
        binding.root.name.text = userDetails.name
    }
}