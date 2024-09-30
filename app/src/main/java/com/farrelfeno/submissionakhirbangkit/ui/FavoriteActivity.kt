package com.farrelfeno.submissionakhirbangkit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.farrelfeno.submissionakhirbangkit.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteviewmodel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        adapter = FavoriteAdapter()
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        binding.rvUser.adapter = adapter

        val factory = ViewModelFactory.getInstance(application)
        favoriteviewmodel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteviewmodel.getFavoriteUsers()?.observe(this){favoriteList ->
            favoriteList?.let {
                adapter.submitList(it)
            }
        }
    }
}