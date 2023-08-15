package com.shpp.application.level_2.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.ItemUserBinding
import com.shpp.application.level_2.model.User

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {


    class UsersViewHolder (
        val binding: ItemUserBinding
    ):RecyclerView.ViewHolder(binding.root)


    var users: List<User> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value;
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            name.text = user.name
            job.text = user.job
            if (user.photo.isNotBlank()) {
                Glide.with(avatarUser.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(avatarUser)
            } else {
                avatarUser.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }

    override fun getItemCount(): Int = users.size
}