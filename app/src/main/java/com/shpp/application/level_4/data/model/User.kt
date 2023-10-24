package com.shpp.application.level_4.data.model

import java.util.UUID

/**
 * User.kt
 * @author Pavlo Kokhanevych
 */
data class User(
    val name: String,
    val job: String,
    val address: String,
    val email: String,
    val birth: String = "11.01.2002",
    val phone: String,
    val photo: String?,
    val id: UUID = UUID.randomUUID(),
)
