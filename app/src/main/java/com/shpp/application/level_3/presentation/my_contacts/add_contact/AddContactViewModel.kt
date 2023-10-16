package com.shpp.application.level_3.presentation.my_contacts.add_contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shpp.application.level_3.App
import com.shpp.application.level_3.data.model.User
import com.shpp.application.level_3.data.repository.UserRepository

class AddContactViewModel: ViewModel() {

    private val usersService: UserRepository = App.userRepository
    val users: LiveData<List<User>> = usersService.users

    fun addUser(
        user: User,
        index: Int? = null
    ) {
        usersService.addUser(user, index)
    }
}