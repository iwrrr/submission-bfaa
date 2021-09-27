package com.dicoding.bfaa.submission.util

import com.dicoding.bfaa.submission.model.User

interface OnItemClickCallback {
    fun onItemClicked(data: User)
}