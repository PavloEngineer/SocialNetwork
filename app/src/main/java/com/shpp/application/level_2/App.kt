package com.shpp.application.level_2

import android.app.Application
import android.content.ContentResolver
import com.shpp.application.level_2.data.repository.UserRepository

class App : Application() {      //todo arch https://developer.android.com/courses/pathways/android-architecture

    override fun onCreate() {
        _userRepository = UserRepository()
        super.onCreate()
    }


    companion object {
        private lateinit var _userRepository: UserRepository

        val userRepository: UserRepository
            get() = _userRepository
    }
}
