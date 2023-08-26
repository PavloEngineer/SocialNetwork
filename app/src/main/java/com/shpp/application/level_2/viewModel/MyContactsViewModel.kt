package com.shpp.application.level_2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shpp.application.level_2.model.User
import com.shpp.application.level_2.model.UserListeners
import com.shpp.application.level_2.model.UserService

class MyContactsViewModel(
    private val usersService: UserService
) : ViewModel() {

    private val usersPrivate = MutableLiveData<List<User>>()
    val users: MutableLiveData<List<User>> = usersPrivate
    private var listener: UserListeners = {
        usersPrivate.value = it
    }

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        usersService.deleteListener(listener)
    }


    fun loadUsers() {
        usersService.addListener(listener)
    }

    fun deleteUser(user: User) {
        usersService.deleteUser(user)
    }

    fun addUser(user: User, index: Int) {
        usersService.addUser(user, index)
    }

    fun addUser(user: User) {
        usersService.addUser(user)
    }

}