package com.jeff.gitusers.main.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.jeff.gitusers.R
import com.jeff.gitusers.databinding.ActivityDetailBinding
import com.jeff.gitusers.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        private var EXTRA_ID = "EXTRA_ID"
        private var EXTRA_AVATAR_URL = "EXTRA_AVATAR_URL"

        fun getStartIntent(
            context: Context,
            id : Int,
            avatarUrl : String


        ): Intent {
            return Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_AVATAR_URL, avatarUrl)
        }
    }

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setupToolbar()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setupToolbar() {
        binding.toolbarLayout.title = intent.getStringExtra(EXTRA_ID)
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
}