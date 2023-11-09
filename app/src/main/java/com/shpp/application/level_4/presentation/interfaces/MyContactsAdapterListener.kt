package com.shpp.application.level_4.presentation.interfaces

import androidx.navigation.fragment.FragmentNavigator
import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.presentation.fragments.my_contacts.model.ContactItem

interface MyContactsAdapterListener {

    fun onClick(contact: User, extras: FragmentNavigator.Extras)

    fun onDeleteClick(contact: User)

    fun onLongClick(contactItem: ContactItem)

    fun onCheckClick(contact: User, isChecked: Boolean)

}