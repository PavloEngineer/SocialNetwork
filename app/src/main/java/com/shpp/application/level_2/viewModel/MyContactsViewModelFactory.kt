package com.shpp.application.level_2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shpp.application.level_2.App

class MyContactsViewModelFactory(
    private val app: App
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            MyContactsViewModel::class.java -> {
                MyContactsViewModel(app.userService)
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
        return viewModel as T
    }
}
