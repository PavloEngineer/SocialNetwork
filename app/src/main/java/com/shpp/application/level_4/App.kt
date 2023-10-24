package com.shpp.application.level_4

import android.app.Application
import com.shpp.application.level_4.data.repository.UserRepository

class App : Application() {

    override fun onCreate() {
        _userRepository = UserRepository()
        super.onCreate()
    }

    companion object {
        private lateinit var _userRepository: UserRepository
        const val isFeatureNavigationEnable: Boolean = true
        var email: String? = null

        val userRepository: UserRepository
            get() = _userRepository
    }
}
