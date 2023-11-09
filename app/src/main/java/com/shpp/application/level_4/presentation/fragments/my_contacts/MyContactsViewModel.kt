package com.shpp.application.level_4.presentation.fragments.my_contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shpp.application.level_4.App
import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.data.repository.UserRepository
import com.shpp.application.level_4.presentation.fragments.my_contacts.model.ContactItem

/**
 * MyContactsViewModel.kt
 * @author Pavlo Kokhanevych
 */
class MyContactsViewModel (
) : ViewModel() {

    private val usersService: UserRepository = App.userRepository
    val users: LiveData<List<User>> = usersService.users

    val contactsLiveData: LiveData<List<ContactItem>> get() = _contactsLiveData
    val selectionModeLiveData: LiveData<Boolean> get() = _selectionModeLiveData

    private val _contactsLiveData = MutableLiveData<List<ContactItem>>()
    private val _selectionModeLiveData = MutableLiveData<Boolean>()

    init {
        _contactsLiveData.value = users.value?.map { ContactItem(it, false) } ?: emptyList()
        _selectionModeLiveData.value = false
    }

    // Метод для вмикання режиму виділення
    fun enableSelectionMode() {
        _selectionModeLiveData.value = true
    }

    // Метод для вимикання режиму виділення
    fun disableSelectionMode() {
        _selectionModeLiveData.value = false
        // Скидайте виділення для всіх контактів
        _contactsLiveData.value = _contactsLiveData.value?.map { it.copy(isChecked = false) }
    }

    // Метод для видалення виділених контактів
    fun deleteSelectedContacts() {
        usersService.deleteSelectedItems(_contactsLiveData.value)
        _contactsLiveData.value = users.value?.map { ContactItem(it, false) } ?: emptyList()
    }

    fun toggle(contactItem: ContactItem) {
        val updatedContacts = _contactsLiveData.value?.toMutableList() ?: mutableListOf()
        val contactIndex = updatedContacts.indexOf(updatedContacts.find { contactItem.id == it.id })

        if (contactIndex != -1) {
            updatedContacts[contactIndex] = contactItem
            _contactsLiveData.value = updatedContacts
        }
    }

    fun isCheck(user: User): Boolean {
        val contactItem = _contactsLiveData.value?.find { user.id == it.id }
        if (contactItem != null) {
            return contactItem.isChecked
        }
        return false
    }

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