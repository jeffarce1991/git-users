package com.jeff.gitusers.main.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.gitusers.R
import com.jeff.gitusers.android.base.extension.formatNumberToAcronym
import com.jeff.gitusers.android.base.extension.hide
import com.jeff.gitusers.database.local.UserDetails
import com.jeff.gitusers.databinding.ActivityDetailBinding
import com.jeff.gitusers.main.detail.presenter.UserDetailsPresenter
import dagger.android.AndroidInjection
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


        userDetailsPresenter.loadUserDetails(getUserName()!!, getId()!!)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            userDetailsPresenter.loadUserDetails(getUserName()!!, getId()!!)
        }
    }

    private fun getUserName(): String? = intent.getStringExtra(EXTRA_LOGIN)
    private fun getId(): Int? = intent.getIntExtra(EXTRA_ID, -1)

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
        userDetails.let {
            binding.root.followers.text = formatNumberToAcronym(it.followers!!)
            binding.root.following.text = formatNumberToAcronym(it.following!!)

            binding.root.name.text = it.name
            binding.root.location.text = it.location
            binding.root.company.text = it.company
            binding.root.blog.text = it.blog }
    }

    override fun showMessage(message: String) {
        Snackbar.make(binding.coordLayout,
            message,
            Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun startShimmerAnimations() {
        binding.root.shimmer_details_container.startShimmerAnimation()
        binding.root.shimmer_follows_container.startShimmerAnimation()
    }

    override fun hideShimmerPlaceholders() {
        binding.root.followers_shimmer.hide()
        binding.root.following_shimmer.hide()
        binding.root.name_shimmer.hide()
        binding.root.location_shimmer.hide()
        binding.root.company_shimmer.hide()
        binding.root.blog_shimmer.hide()
    }

    override fun stopShimmerAnimations() {
        binding.root.shimmer_details_container.stopShimmerAnimation();
        binding.root.shimmer_follows_container.stopShimmerAnimation();
    }
}