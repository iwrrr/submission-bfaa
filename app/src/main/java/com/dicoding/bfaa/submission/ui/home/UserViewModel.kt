package com.dicoding.bfaa.submission.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.model.UserResponse
import com.dicoding.bfaa.submission.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _user = MutableLiveData<ArrayList<User>>()
    val user: LiveData<ArrayList<User>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchUsers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.users
                } else {
                    Log.d(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                t.printStackTrace()
            }
        })
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}