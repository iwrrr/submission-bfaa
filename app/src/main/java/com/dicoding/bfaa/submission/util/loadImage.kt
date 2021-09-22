package com.dicoding.bfaa.submission.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(image: Int?) {
    Glide.with(this.context)
        .load(image)
        .circleCrop()
        .into(this)
}