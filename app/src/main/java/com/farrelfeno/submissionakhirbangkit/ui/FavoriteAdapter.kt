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
import com.farrelfeno.submissionakhirbangkit.database.FavoriteUsers
import com.farrelfeno.submissionakhirbangkit.databinding.ItemUsersBinding
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem

class FavoriteAdapter : ListAdapter<FavoriteUsers, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    private val list = ArrayList<FavoriteUsers>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val view = getItem(position)
        holder.bind(view)
    }


    inner class FavoriteViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val iv_users: ImageView = binding.ivUsers
        val tv_username: TextView = binding.tvUsername
        fun bind(users:FavoriteUsers) {
            binding.root.setOnClickListener {
                with(binding.root.context){
                    Intent(this, DetailUsersActivity::class.java).apply {
                        putExtra(DetailUsersActivity.EXTRA_USERSNAME, users.usersname)
                        startActivity(this)
                    }
                }

            }
            Glide.with(itemView)
                .load(users.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(iv_users)
            tv_username.text = users.usersname
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUsers>() {
            override fun areItemsTheSame(
                oldItem:FavoriteUsers,
                newItem:FavoriteUsers
            ): Boolean {
                return oldItem.usersname == newItem.usersname
            }

            override fun areContentsTheSame(
                oldItem:FavoriteUsers,
                newItem:FavoriteUsers
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}