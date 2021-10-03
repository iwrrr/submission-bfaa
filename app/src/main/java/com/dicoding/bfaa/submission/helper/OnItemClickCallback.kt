package com.dicoding.bfaa.submission.helper

import com.dicoding.bfaa.submission.model.User

interface OnItemClickCallback {
    fun onItemClicked(data: User)
}