package com.shpp.application.level_2.presentation.my_contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shpp.application.level_2.App
import com.shpp.application.level_2.data.model.User
import com.shpp.application.level_2.data.repository.UserRepository

/**
 * MyContactsViewModel.kt
 * @author Pavlo Kokhanevych
 */
class MyContactsViewModel : ViewModel() {

    private val usersService: UserRepository = App.userRepository
    val users: LiveData<List<User>> = usersService.users


    fun restoreLastDeletedUser() {
        usersService.restoreLastDeletedUser()
    }

    fun deleteUser(user: User) {
        usersService.deleteUser(user)
    }

    fun addUser(
        user: User,
        index: Int? = null
    ) {
        usersService.addUser(user, index)
    }

    fun deleteUserByPosition(position: Int) {
        users.value?.let {
            deleteUser(it[position])
        }
    }


}