package com.shpp.application.level_4.presentation.fragments.my_contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shpp.application.level_4.App
import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.data.repository.UserRepository
import com.shpp.application.level_4.presentation.multiselect.ContactItem
import com.shpp.application.level_4.presentation.multiselect.MultiSelectManager

/**
 * MyContactsViewModel.kt
 * @author Pavlo Kokhanevych
 */
class MyContactsViewModel (
) : ViewModel() {

    private val usersService: UserRepository = App.userRepository
    val users: LiveData<List<User>> = usersService.users

    private val multiSelectManager = MultiSelectManager(usersService.users.value)


    fun enableSelectionMode() {
        multiSelectManager.enableSelectionMode()
    }

    fun disableSelectionMode() {
        multiSelectManager.disableSelectionMode()
    }

    fun deleteSelectedContacts() {
        usersService.deleteSelectedItems(multiSelectManager.contactsLiveData.value)
    }

    fun toggle(contactItem: ContactItem) {
        multiSelectManager.toggle(contactItem)
    }

    fun isCheck(user: User): Boolean {
        return multiSelectManager.isCheck(user)
    }

    fun isSelectionModeEnabled(): Boolean {
        return multiSelectManager.selectionModeLiveData.value ?: false
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
       multiSelectManager.updateContactsSelectionMode(users.value)
    }

    fun getSelectionModeLiveData(): LiveData<Boolean> {
        return multiSelectManager.selectionModeLiveData
    }
}