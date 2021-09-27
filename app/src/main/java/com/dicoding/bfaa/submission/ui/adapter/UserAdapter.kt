package com.dicoding.bfaa.submission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.submission.databinding.ItemUserBinding
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.util.OnItemClickCallback
import com.dicoding.bfaa.submission.util.loadImage

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val list = ArrayList<User>()

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                tvUsername.text = user.username
                tvUrl.text = user.url
                ivPhoto.loadImage(user.avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}