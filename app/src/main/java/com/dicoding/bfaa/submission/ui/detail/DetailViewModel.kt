package com.dicoding.bfaa.submission.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bfaa.submission.model.DetailUser
import com.dicoding.bfaa.submission.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private var _user = MutableLiveData<DetailUser>()
    val user: LiveData<DetailUser> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
                Log.d(TAG, "onResponse: ${response.message()}")
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}