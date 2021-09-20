package com.dicoding.bfaa.submission.util

import com.dicoding.bfaa.submission.entity.User

interface OnItemClickCallback {
    fun onItemClicked(data: User)
}