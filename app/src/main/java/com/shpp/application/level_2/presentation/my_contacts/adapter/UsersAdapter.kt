package com.shpp.application.level_2.presentation.my_contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.databinding.ItemUserBinding
import com.shpp.application.level_2.data.model.User
import com.shpp.application.level_2.presentation.callBacks.diffutil.UserDiffUtilCallBack
import com.shpp.application.level_2.presentation.my_contacts.interfaces.MyContactsAdapterListener
import com.shpp.application.level_2.presentation.utils.extensions.downloadAndPutPhoto

class UsersAdapter(
        val listener: MyContactsAdapterListener
) : ListAdapter<User, UsersAdapter.UsersViewHolder>(UserDiffUtilCallBack()) {


    inner class UsersViewHolder(
            private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                name.text = user.name
                job.text = user.job
                basket.tag = user
                basket.setOnClickListener {
                    listener.onDeleteClick(user)
                }
                root.setOnClickListener {
                    listener.onClick(user)
                }
                avatarUser.downloadAndPutPhoto(user.photo)
            }
        }

    }

    fun onSwipedContact(position: Int) {
        listener.onDeleteClick(currentList[position])
    }


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(
                inflater,
                parent,
                false
        )
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(
            holder: UsersViewHolder,
            position: Int
    ) = holder.bind(currentList[position])


}