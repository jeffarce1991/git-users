package com.jeff.gitusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jakewharton.picasso.OkHttp3Downloader
import com.jeff.gitusers.R
import com.jeff.gitusers.adapter.CustomAdapter.CustomViewHolder
import com.jeff.gitusers.android.base.extension.shortToast
import com.jeff.gitusers.database.local.Photo
import com.jeff.gitusers.databinding.CustomRowBinding
import com.jeff.gitusers.main.view.MainActivity
import com.squareup.picasso.Picasso

internal class CustomAdapter(
    private val context: Context,
    private val dataList: List<Photo>
) : RecyclerView.Adapter<CustomViewHolder>() {

    internal inner class CustomViewHolder(binding: CustomRowBinding) :
        ViewHolder(binding.root) {
        var itemLayout: ConstraintLayout = binding.itemLayout
        var txtTitle: TextView = binding.customRowTitle
        val coverImage: ImageView = binding.coverImage

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<CustomRowBinding>(LayoutInflater.from(p0.context),
            R.layout.custom_row,
            p0,
            false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = dataList[position]
        holder.txtTitle.text = item.title
        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(item.thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage)

        holder.itemLayout.setOnClickListener {
            val context = context as MainActivity
            context.shortToast("${item.id}")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}