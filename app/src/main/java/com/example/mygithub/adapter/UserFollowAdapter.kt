package com.example.mygithub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithub.R
import com.example.mygithub.model.UserFollowResponse
import kotlinx.android.synthetic.main.item_followers_layout.view.*

class UserFollowAdapter :
    PagingDataAdapter<UserFollowResponse.UserFollowResponseItem, UserFollowAdapter.MyViewHolder>(
        FOLLOWERS_COMPARATOR
    ) {
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            Glide.with(holder.itemView).load(currentItem.avatar_url).centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade()).error(
                    R.drawable.ic_error
                ).into(holder.itemView.dp)
            holder.itemView.user_name.text = currentItem.login
            holder.itemView.index.text = (position + 1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_followers_layout, parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    companion object {
        private val FOLLOWERS_COMPARATOR =
            object : DiffUtil.ItemCallback<UserFollowResponse.UserFollowResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: UserFollowResponse.UserFollowResponseItem,
                    newItem: UserFollowResponse.UserFollowResponseItem
                ) = oldItem.login == newItem.login

                override fun areContentsTheSame(
                    oldItem: UserFollowResponse.UserFollowResponseItem,
                    newItem: UserFollowResponse.UserFollowResponseItem
                ) = oldItem == newItem
            }
    }
}