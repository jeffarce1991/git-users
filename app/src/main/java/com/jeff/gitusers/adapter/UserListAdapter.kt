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
import com.bumptech.glide.Glide
import com.jeff.gitusers.R
import com.jeff.gitusers.adapter.UserListAdapter.UserViewHolder
import com.jeff.gitusers.android.base.extension.invertColor
import com.jeff.gitusers.android.base.extension.shortToast
import com.jeff.gitusers.database.local.User
import com.jeff.gitusers.databinding.ItemLoadingBinding
import com.jeff.gitusers.databinding.ItemUserBinding
import com.jeff.gitusers.main.detail.view.DetailActivity
import com.jeff.gitusers.main.list.view.MainActivity


internal class UserListAdapter(
    private val context: Context,
    private var userList: MutableList<User>
) : RecyclerView.Adapter<UserViewHolder>() {
    
    internal inner class UserViewHolder(binding: ItemUserBinding) :
        ViewHolder(binding.root) {
        var itemLayout: ConstraintLayout = binding.itemLayout
        var txtTitle: TextView = binding.customRowTitle
        val coverImage: ImageView = binding.coverImage
        val txtDetails: TextView = binding.details
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserBinding>(LayoutInflater.from(p0.context),
            R.layout.item_user,
            p0,
            false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userList[position]
        holder.txtTitle.text = item.login
        holder.txtDetails.text = item.htmlUrl

        Glide
            .with(context)
            .load(item.avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.coverImage)

        invertEvery4thItem(holder.coverImage, position)

        holder.itemLayout.setOnClickListener {
            val intent = DetailActivity.getStartIntent(
                context,
                item.id,
                item.avatarUrl
            )
            context.startActivity(intent)
        }
    }

    private fun invertEvery4thItem(imageView: ImageView, position: Int){
        if (position%4 == 0 && position != 0 ) {
            imageView.invertColor()
        } else {
            imageView.clearColorFilter()
        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    fun update(users: List<User>) {
        this.userList = users as MutableList<User>
        notifyDataSetChanged()
    }

    fun getAll(): List<User> {
        return this.userList
    }

    fun addAll(users: List<User>) {
        userList.addAll(users)
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return userList.size
    }

    fun getLastIndexId(): Int {
        return userList.last().id
    }

}