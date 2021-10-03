package com.dicoding.bfaa.submission.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.bfaa.submission.ui.detail.DetailViewModel
import com.dicoding.bfaa.submission.ui.favorite.FavoriteViewModel
import com.dicoding.bfaa.submission.ui.settings.SettingPreferences
import com.dicoding.bfaa.submission.ui.settings.SettingsViewModel

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    private var preferences: SettingPreferences? = null

    constructor(preferences: SettingPreferences) : this(Application()) {
        this.preferences = preferences
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                preferences?.let { SettingsViewModel(it) } as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}