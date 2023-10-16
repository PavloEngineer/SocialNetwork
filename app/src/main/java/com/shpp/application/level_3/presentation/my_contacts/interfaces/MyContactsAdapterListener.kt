package com.shpp.application.level_3.presentation.my_contacts.interfaces

import androidx.navigation.fragment.FragmentNavigator
import com.shpp.application.level_3.data.model.User

interface MyContactsAdapterListener {

    fun onClick(contact: User, extras: FragmentNavigator.Extras)

    fun onDeleteClick(contact: User)
}