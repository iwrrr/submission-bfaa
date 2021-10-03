package com.dicoding.bfaa.submission.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.bfaa.submission.model.User

class UserDiffCallback(private val mOldUser: List<User>, private val mNewUser: List<User>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldUser.size
    }

    override fun getNewListSize(): Int {
        return mNewUser.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUser[oldItemPosition].id == mNewUser[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUser[oldItemPosition]
        val newUser = mNewUser[newItemPosition]
        return oldUser.id == newUser.id &&
                oldUser.username == newUser.username &&
                oldUser.avatar == newUser.avatar &&
                oldUser.url == newUser.url
    }
}