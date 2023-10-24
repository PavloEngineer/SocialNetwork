package com.shpp.application.level_4.presentation.callBacks.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.shpp.application.level_4.data.model.User

class UserDiffUtilCallBack : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean = oldItem == newItem

}