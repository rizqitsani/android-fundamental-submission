package com.rizqitsani.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rizqitsani.githubuser.data.database.Favorite
import com.rizqitsani.githubuser.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}