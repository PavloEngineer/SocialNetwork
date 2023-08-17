package com.shpp.application.level_2.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.ItemUserBinding
import com.shpp.application.level_2.model.User


class UserDiffCallBack (
    private val oldList: List<User>,
    private val newList: List<User>
        ): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition] == newList[newItemPosition]
    }


}

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {


    class UsersViewHolder (
        val binding: ItemUserBinding
    ):RecyclerView.ViewHolder(binding.root)


    var users: List<User> = emptyList()
        set(value) {
            val diffUtil = UserDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            field = value;
            diffResult.dispatchUpdatesTo(this)
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