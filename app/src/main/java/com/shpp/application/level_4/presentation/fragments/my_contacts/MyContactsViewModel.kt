package com.shpp.application.level_4.presentation.fragments.my_contacts

import android.view.View
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

    val selectionModeLiveData: LiveData<Boolean> get() = _selectionModeLiveData

    val contactsLiveData: LiveData<List<ContactItem>> get() = _contactsLiveData
    private val _contactsLiveData = MutableLiveData<List<ContactItem>>()
    private val _selectionModeLiveData = MutableLiveData<Boolean>()

    init {
        _selectionModeLiveData.value = false
    }

    // Метод для вмикання режиму виділення
    fun enableSelectionMode() {
        _selectionModeLiveData.value = true
    }

    // Метод для вимикання режиму виділення
    fun disableSelectionMode() {
        val hasLeastOneCheckContact = contactsLiveData.value?.find { it.isChecked }
        if (hasLeastOneCheckContact == null) {
            _selectionModeLiveData.value = false
        }
    }

    // Метод для видалення виділених контактів
    fun deleteSelectedContacts() {
        usersService.deleteSelectedItems(_contactsLiveData.value)
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

    fun deleteUserByPosition(position: Int) {
        users.value?.let {
            deleteUser(it[position])
        }
    }

   fun updateContactsSelectionMode() {
       if (_contactsLiveData.isInitialized) {
           _contactsLiveData.value = users.value?.map { user ->
               ContactItem(user, _contactsLiveData.value?.find {  it.id == user.id}?.isChecked ?: false)
           } ?: emptyList()
       } else {
           _contactsLiveData.value = users.value?.map { ContactItem(it, false) } ?: emptyList()
       }
    }
}