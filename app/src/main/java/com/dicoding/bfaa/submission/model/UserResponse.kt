package com.dicoding.bfaa.submission.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("items")
    val users: ArrayList<User>,
)