package com.rizqitsani.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rizqitsani.githubuser.data.database.Favorite
import com.rizqitsani.githubuser.data.database.FavoriteDao
import com.rizqitsani.githubuser.data.database.FavoriteRoomDatabase

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun getFavoriteByLogin(login: String): LiveData<Favorite> =
        mFavoriteDao.getFavoriteByLogin(login)

    suspend fun insert(favorite: Favorite) {
        mFavoriteDao.insert(favorite)
    }

    suspend fun delete(login: String) {
        mFavoriteDao.delete(login)
    }
}