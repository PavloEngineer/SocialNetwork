package com.shpp.application.level_3

import android.app.Application
import com.shpp.application.level_3.data.repository.UserRepository

class App : Application() {

    override fun onCreate() {
        _userRepository = UserRepository()
        super.onCreate()
    }


    companion object {
        private lateinit var _userRepository: UserRepository
        const val isFeatureNavigationEnable: Boolean = true

        val userRepository: UserRepository
            get() = _userRepository
    }
}
