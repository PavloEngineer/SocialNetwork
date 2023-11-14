package com.shpp.application.level_4.presentation.interfaces

import com.shpp.application.level_4.data.model.User

interface ContactSelectionListener {

    fun onContactSelectionActivated()

    fun disableSelectionMode()

    fun enableSelectionMode()

    fun isSelectionModeEnabled(): Boolean

    fun isCheck(user: User): Boolean

    fun onCheckClick(contact: User, isChecked: Boolean)
}