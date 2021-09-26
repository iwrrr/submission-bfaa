package com.dicoding.bfaa.submission.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("login")
    val username: String? = null,

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
    val repository: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null,
) : Parcelable