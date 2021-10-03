package com.dicoding.bfaa.submission.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.bfaa.submission.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoriteUser() = mFavoriteRepository.getFavoriteUser()
}