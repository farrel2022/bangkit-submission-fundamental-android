package com.farrelfeno.submissionakhirbangkit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.farrelfeno.submissionakhirbangkit.R
import com.farrelfeno.submissionakhirbangkit.database.FavoriteUsers
import com.farrelfeno.submissionakhirbangkit.databinding.ActivityDetailUsersBinding
import com.google.android.material.tabs.TabLayoutMediator


class DetailUsersActivity : AppCompatActivity() {

    private  lateinit var  favoriteUser : FavoriteUsers

    companion object{
        const val EXTRA_USERSNAME = "extra_usersname"
    }

    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var detailviewmodel: DetailUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersname = intent.getStringExtra(EXTRA_USERSNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERSNAME, usersname)

        val factory = ViewModelFactory.getInstance(application)
        detailviewmodel = ViewModelProvider(this, factory)[DetailUsersViewModel::class.java]

        if (usersname != null) {
            detailviewmodel.checkUser(usersname)
            detailviewmodel.setUsersDetail(usersname)
        }
        detailviewmodel.setUsersDetail(usersname.toString())
        detailviewmodel.listUsers.observe(this) {
            favoriteUser = FavoriteUsers(it.login, it.avatarUrl)
            if (it != null) {
                with(binding) {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollower.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUsersActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }
            showLoading(false)
        }
        detailviewmodel.isFavoriteAdded.observe(this){
            favoriteButtonStatus()
        }

        binding.toggleFavorite.setOnClickListener {
            if (detailviewmodel.isFavoriteAdded.value == true) {
                detailviewmodel.deleteUser(favoriteUser)
            } else {
                detailviewmodel.addUser(favoriteUser)
            }
            detailviewmodel.isFavoriteAdded.value  = !(detailviewmodel.isFavoriteAdded.value?:false)
        }

        val adapter = SectionPageAdapter(this, usersname)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()
    }


    private fun favoriteButtonStatus() {
        if (detailviewmodel.isFavoriteAdded.value == true) {
            binding.toggleFavorite.setImageResource(R.drawable.ic_heart_red)
        } else {
            binding.toggleFavorite.setImageResource(R.drawable.ic_heart_black)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}