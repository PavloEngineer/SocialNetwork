package com.shpp.application.level_2

import android.app.Application
import com.shpp.application.level_2.model.UserService

class App: Application() {
    val userService = UserService()
}