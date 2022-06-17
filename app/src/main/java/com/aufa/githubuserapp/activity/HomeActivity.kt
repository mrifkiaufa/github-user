package com.aufa.githubuserapp.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aufa.githubuserapp.R
import com.aufa.githubuserapp.adapter.ListUserAdapter
import com.aufa.githubuserapp.data.ItemsItem
import com.aufa.githubuserapp.databinding.ActivityHomeBinding
import com.aufa.githubuserapp.model.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var gitHubUser: RecyclerView

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var activityHomeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        gitHubUser = findViewById(R.id.github_user)
        gitHubUser.setHasFixedSize(true)

        homeViewModel.searchData.observe(this) { searchData ->
            setSearchData(searchData)
        }

        homeViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.boomark -> {
                val i = Intent(this, BoomarkActivity::class.java)
                startActivity(i)
                true
            }

            R.id.theme -> {
                val i = Intent(this, ThemeActivity::class.java)
                startActivity(i)
                true
            }

            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                homeViewModel.findUser(query)
                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun setSearchData(searchResult: List<ItemsItem>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gitHubUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            gitHubUser.layoutManager = LinearLayoutManager(this)
        }

        val adapter = ListUserAdapter(searchResult)
        gitHubUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityHomeBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}