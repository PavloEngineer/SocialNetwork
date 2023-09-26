package com.shpp.application.level_2.presentation.my_contacts.interfaces

import com.shpp.application.level_2.data.model.User

interface MyContactsAdapterListener {

    fun onClick(contact: User)

    fun onDeleteClick(contact: User)


}