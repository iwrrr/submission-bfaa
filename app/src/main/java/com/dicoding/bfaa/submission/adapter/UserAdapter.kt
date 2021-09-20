package com.dicoding.bfaa.submission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bfaa.submission.databinding.ItemUserBinding
import com.dicoding.bfaa.submission.entity.User
import com.dicoding.bfaa.submission.util.OnItemClickCallback

class UserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                tvUsername.text = user.name
                tvTotalRepo.text = user.repository.toString()
                tvTotalFollowers.text = user.followers.toString()
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .circleCrop()
                    .into(ivPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[position])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}