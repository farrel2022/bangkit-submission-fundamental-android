package com.farrelfeno.submissionakhirbangkit.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.farrelfeno.submissionakhirbangkit.databinding.ItemUsersBinding
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem

class MainAdapter : ListAdapter<ItemsItem, MainAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = getItem(position)
        holder.bind(view)
    }

    inner class MyViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val iv_users: ImageView = binding.ivUsers
        val tv_username: TextView = binding.tvUsername
        fun bind(users: ItemsItem) {
            binding.root.setOnClickListener {
                with(binding.root.context){
                Intent(this, DetailUsersActivity::class.java).apply {
                        putExtra(DetailUsersActivity.EXTRA_USERSNAME, users.login)
                        startActivity(this)
                    }
                }

            }
            Glide.with(itemView)
                .load(users.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(iv_users)
            tv_username.text = users.login
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}