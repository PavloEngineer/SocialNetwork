package com.shpp.application.level_4.presentation.interfaces

import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.presentation.fragments.my_contacts.model.ContactItem

interface ContactSelectionListener {

    fun onContactSelectionChanged(item: ContactItem)

    fun isCheck(user: User): Boolean
}