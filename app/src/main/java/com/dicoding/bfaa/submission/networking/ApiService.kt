package com.dicoding.bfaa.submission.networking

import com.dicoding.bfaa.submission.data.model.DetailUser
import com.dicoding.bfaa.submission.data.model.User
import com.dicoding.bfaa.submission.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}