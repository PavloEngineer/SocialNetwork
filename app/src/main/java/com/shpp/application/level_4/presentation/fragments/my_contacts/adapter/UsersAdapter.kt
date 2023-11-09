package com.shpp.application.level_4.presentation.fragments.my_contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.ItemUserBinding
import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.presentation.callBacks.diffutil.UserDiffUtilCallBack
import com.shpp.application.level_4.presentation.fragments.my_contacts.model.ContactItem
import com.shpp.application.level_4.presentation.interfaces.ContactSelectionListener
import com.shpp.application.level_4.presentation.interfaces.MyContactsAdapterListener
import com.shpp.application.level_4.presentation.utils.extensions.downloadAndPutPhoto
import com.shpp.application.level_4.utils.Constants.TRANSACTION_PHOTO

class UsersAdapter(
    val listener: MyContactsAdapterListener,
    private val contactSelectionListener: ContactSelectionListener
) : ListAdapter<User, UsersAdapter.UsersViewHolder>(UserDiffUtilCallBack()) {

    private var isMultiSelectionEnabled = false

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

            if (!isMultiSelectionEnabled) {
                changeDesignToSimpleState()
            }
        }

        private fun changeDesignToSimpleState() {
            with(binding) {
                buttonBasket.visibility = View.VISIBLE
                checkboxItem.visibility = View.GONE
                val background = ContextCompat.getDrawable(binding.root.context, R.drawable.item_border)
                root.background = background
            }
        }

        private fun setListeners(user: User) {
            with (binding) {
                root.setOnClickListener {
                    val extras = FragmentNavigatorExtras(
                        avatarUser to TRANSACTION_PHOTO
                    )
                    avatarUser.transitionName = "transaction_${user.id}"
                    listener.onClick(user, extras)
                }

                root.setOnLongClickListener {
                    if (!isMultiSelectionEnabled) {
                        contactSelectionListener.onContactSelectionChanged(ContactItem(user, false))
                        isMultiSelectionEnabled = true
                    }
                    true
                }

                checkboxItem.setOnClickListener {
                    listener.onCheckClick(user, checkboxItem.isChecked)
                }

                buttonBasket.setOnClickListener {
                    listener.onDeleteClick(user)
                }
            }
        }

        fun bindForMultiSelect(contactItem: ContactItem) {
            with(binding) {
                checkboxItem.isChecked = contactItem.isChecked
                changeDesignForMultiSelect()
            }
        }

        private fun changeDesignForMultiSelect() {
            with(binding) {
                buttonBasket.visibility = View.GONE
                checkboxItem.visibility = View.VISIBLE
                val background = ContextCompat.getDrawable(binding.root.context, R.drawable.item_border_select)
                root.background = background
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
    ){
        holder.bind(currentList[position])
        if (isMultiSelectionEnabled) {
            holder.bindForMultiSelect(ContactItem(getItem(position), contactSelectionListener.isCheck(getItem(position))))
        }
    }
}
