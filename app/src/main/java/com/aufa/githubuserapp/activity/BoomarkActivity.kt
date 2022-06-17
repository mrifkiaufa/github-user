package com.aufa.githubuserapp.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aufa.githubuserapp.R
import com.aufa.githubuserapp.adapter.ListUserAdapter
import com.aufa.githubuserapp.data.ItemsItem
import com.aufa.githubuserapp.database.Favorite
import com.aufa.githubuserapp.databinding.ActivityBoomarkBinding
import com.aufa.githubuserapp.model.FavoriteViewModel
import com.aufa.githubuserapp.model.FavoriteViewModelFactory

class BoomarkActivity : AppCompatActivity() {
    private lateinit var favoriteList: RecyclerView

    private lateinit var activityBoomarkBinding: ActivityBoomarkBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBoomarkBinding = ActivityBoomarkBinding.inflate(layoutInflater)
        setContentView(activityBoomarkBinding.root)

        val favoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this.application)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]

        favoriteViewModel.dataFavorites.observe(this) { dataFavorites ->
            setAdapter(dataFavorites)
        }
    }

    private fun convertToItems(data: List<Favorite>): List<ItemsItem>{
        val userList = ArrayList<ItemsItem>()

        data.forEach{
            userList.add(ItemsItem(it.login, it.avatarUrl))
        }
        return userList
    }

    private fun setAdapter(dataFavorites: List<Favorite>){
        favoriteList = findViewById(R.id.favorite_list)
        favoriteList.setHasFixedSize(true)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            favoriteList.layoutManager = GridLayoutManager(this, 2)
        } else {
            favoriteList.layoutManager = LinearLayoutManager(this)
        }

        val adapter = ListUserAdapter(convertToItems(dataFavorites))

        if(adapter.itemCount < 1) {
            activityBoomarkBinding.empty.visibility = View.VISIBLE
        }

        favoriteList.adapter = adapter
    }
}