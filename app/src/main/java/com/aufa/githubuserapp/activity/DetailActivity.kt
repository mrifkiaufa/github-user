package com.aufa.githubuserapp.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.aufa.githubuserapp.R
import com.aufa.githubuserapp.adapter.SectionsPagerAdapter
import com.aufa.githubuserapp.data.DetailUserResponse
import com.aufa.githubuserapp.database.Favorite
import com.aufa.githubuserapp.databinding.ActivityDetailBinding
import com.aufa.githubuserapp.model.DetailViewModel
import com.aufa.githubuserapp.model.DetailViewModelFactory
import com.aufa.githubuserapp.model.FavoriteViewModel
import com.aufa.githubuserapp.model.FavoriteViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val username = intent.extras?.get("DATA").toString()

        val detailViewModelFactory = DetailViewModelFactory(username)
        detailViewModel = ViewModelProvider(this, detailViewModelFactory)[DetailViewModel::class.java]

        val favoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this.application)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]

        detailViewModel.dataUser.observe(this) { detailDataUser ->
            val favoriteData = setDetailUser(detailDataUser)
            checkBoomark(detailDataUser.login, favoriteViewModel, favoriteData)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setDetailUser(dataDetail: DetailUserResponse): Favorite {
        val favoriteData: Favorite
        Glide.with(this)
            .load(dataDetail.avatarUrl)
            .into(activityDetailBinding.detailPhoto)

        activityDetailBinding.apply {
            tvUsername.text = dataDetail.login
            tvName.text = dataDetail.name
            tvCompany.text = dataDetail.company
            tvLocation.text = dataDetail.location
            tvRepository.text = dataDetail.publicRepos.toString()
            tvFollowing.text = dataDetail.following.toString()
            tvFollowers.text = dataDetail.followers.toString()

            favoriteData = Favorite(0, dataDetail.login, dataDetail.avatarUrl)
        }

        return favoriteData
    }

    private fun checkBoomark(username: String, favoriteUser: FavoriteViewModel, data: Favorite) {

        if(favoriteUser.isUserExist(username)) {
            activityDetailBinding.favorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_bookmark_36))

        }
        else {
            activityDetailBinding.favorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_bookmark_border_36))
        }

        activityDetailBinding.favorite.setOnClickListener {
            if(favoriteUser.isUserExist(username)) {
                activityDetailBinding.favorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_bookmark_border_36))
                favoriteUser.delete(data.login)
            }
            else {
                activityDetailBinding.favorite.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_bookmark_36))
                favoriteUser.insert(data)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        activityDetailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
}


