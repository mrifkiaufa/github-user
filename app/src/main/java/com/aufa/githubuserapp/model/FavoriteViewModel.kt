package com.aufa.githubuserapp.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aufa.githubuserapp.database.Favorite
import com.aufa.githubuserapp.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    val dataFavorites: LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()

    fun isUserExist(username: String): Boolean = mFavoriteRepository.isUserExist(username)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(username: String) {
        mFavoriteRepository.delete(username)
    }
}