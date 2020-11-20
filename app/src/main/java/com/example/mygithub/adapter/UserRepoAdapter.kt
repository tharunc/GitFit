package com.example.mygithub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithub.R
import com.example.mygithub.model.UserRepoResponse
import kotlinx.android.synthetic.main.item_repo_layout.view.*

class UserRepoAdapter :
    PagingDataAdapter<UserRepoResponse.UserRepoResponseItem, UserRepoAdapter.MyViewHolder>(
        REPO_COMPARATOR
    ) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.apply {
            repo_name.text = currentItem?.name
            if (currentItem?.description != null) {
                repo_description.text = currentItem.description
                repo_description.visibility = View.VISIBLE
            }
            if (currentItem?.language != null) {
                repo_language.text = currentItem.language
                last_line_layout.visibility = View.VISIBLE
            }
            if (currentItem?.license != null) {
                repo_license.text = currentItem.license.name
                last_line_layout.visibility = View.VISIBLE
            }
            repo_count.text = (position + 1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo_layout, parent, false)
        return MyViewHolder(view)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    companion object {
        private val REPO_COMPARATOR =
            object : DiffUtil.ItemCallback<UserRepoResponse.UserRepoResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: UserRepoResponse.UserRepoResponseItem,
                    newItem: UserRepoResponse.UserRepoResponseItem
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: UserRepoResponse.UserRepoResponseItem,
                    newItem: UserRepoResponse.UserRepoResponseItem
                ) = oldItem == newItem
            }
    }
}