package com.dicoding.bfaa.submission.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.bfaa.submission.database.FavoriteUser
import com.dicoding.bfaa.submission.database.FavoriteUserDao
import com.dicoding.bfaa.submission.database.FavoriteUserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {

    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getFavoriteUser()

    fun addToFavorite(id: Int, username: String, avatar: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                id,
                username,
                avatar,
                url
            )
            mFavoriteUserDao.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = mFavoriteUserDao.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteUserDao.removeFromFavorite(id)
        }
    }
}