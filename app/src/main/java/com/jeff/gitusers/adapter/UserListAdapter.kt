package com.jeff.gitusers.adapter

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
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
import com.jeff.gitusers.adapter.UserListAdapter.CustomViewHolder
import com.jeff.gitusers.android.base.extension.shortToast
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.databinding.CustomRowBinding
import com.jeff.gitusers.main.view.MainActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


internal class UserListAdapter(
    private val context: Context,
    private val dataList: MutableList<User>
) : RecyclerView.Adapter<CustomViewHolder>() {


    internal inner class CustomViewHolder(binding: CustomRowBinding) :
        ViewHolder(binding.root) {
        var itemLayout: ConstraintLayout = binding.itemLayout
        var txtTitle: TextView = binding.customRowTitle
        val coverImage: ImageView = binding.coverImage
        val txtDetails: TextView = binding.details

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
        holder.txtTitle.text = item.login
        holder.txtDetails.text = item.htmlUrl
        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(item.avatarUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage,object: Callback{
                override fun onSuccess() {
                    if (position%4 == 0 && position != 0) {
                        invertColor(holder.coverImage)
                    }
                }

                override fun onError() {
                    TODO("Not yet implemented")
                }
            })



        holder.itemLayout.setOnClickListener {
            val context = context as MainActivity
            context.shortToast("${item.id}")
        }
    }

    fun addAll(users: List<User>) {
        dataList.addAll(users)

        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getLastIndexId(): Int {
        return dataList.last().id
    }

    fun invertColor(imageView: ImageView) {
        val invertMX = floatArrayOf(
            -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
            0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
            0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )

        val invertCM = ColorMatrix(invertMX)

        val filter = ColorMatrixColorFilter(invertCM)
        imageView.colorFilter = filter
    }

}