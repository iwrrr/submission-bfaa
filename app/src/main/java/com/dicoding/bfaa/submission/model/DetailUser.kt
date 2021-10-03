package com.dicoding.bfaa.submission.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("avatar_url")
    val avatar: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("public_repos")
    val repository: Int = 0,

    @field:SerializedName("followers")
    val followers: Int = 0,

    @field:SerializedName("following")
    val following: Int = 0,
) : Parcelable