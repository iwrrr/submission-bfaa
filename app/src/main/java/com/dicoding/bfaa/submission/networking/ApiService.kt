package com.dicoding.bfaa.submission.networking

import com.dicoding.bfaa.submission.BuildConfig
import com.dicoding.bfaa.submission.model.DetailUser
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUser(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}