package com.shpp.application.level_3.presentation.fragments.my_contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.databinding.ItemUserBinding
import com.shpp.application.level_3.data.model.User
import com.shpp.application.level_3.presentation.callBacks.diffutil.UserDiffUtilCallBack
import com.shpp.application.level_3.presentation.interfaces.MyContactsAdapterListener
import com.shpp.application.level_3.presentation.utils.extensions.downloadAndPutPhoto
import com.shpp.application.level_3.utils.Constants.TRANSACTION_PHOTO

class UsersAdapter(
    val listener: MyContactsAdapterListener
) : ListAdapter<User, UsersAdapter.UsersViewHolder>(UserDiffUtilCallBack()) {

    inner class UsersViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                textName.text = user.name
                textViewJob.text = user.job
                buttonBasket.tag = user
                setListeners(user)
                avatarUser.downloadAndPutPhoto(user.photo)
            }
        }

        private fun setListeners(user: User) {
            binding.root.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.avatarUser to TRANSACTION_PHOTO
                )
                binding.avatarUser.transitionName = "transaction_${user.id}"
                listener.onClick(user, extras)
            }

            binding.buttonBasket.setOnClickListener {
                listener.onDeleteClick(user)
            }
        }

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