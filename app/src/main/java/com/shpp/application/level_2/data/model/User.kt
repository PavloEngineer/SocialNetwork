package com.shpp.application.level_2.data.model

/**
 * User.kt
 * @author Pavlo Kokhanevych
 */
data class User(
    var id: Int = -1,
    var name: String,
    var job: String,
    var address: String,
    var email: String,
    var birth: String = "11.01.2002",
    var phone: String,
    var photo: String?
)
