package com.aufa.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.aufa.githubuserapp.database.Favorite
import com.aufa.githubuserapp.database.FavoriteDao
import com.aufa.githubuserapp.database.FavoriteRoomDatabase
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun isUserExist(username: String): Boolean{
        val future = executorService.submit(Callable {mFavoriteDao.isUserExist(username)})

        return future.get()
    }

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(username: String) {
        executorService.execute { mFavoriteDao.delete(username) }
    }
}