package com.shpp.application.level_3.data.model

import java.util.UUID

/**
 * User.kt
 * @author Pavlo Kokhanevych
 */
data class User( // TODO: var?
    var name: String,
    var job: String,
    var address: String,
    var email: String,
    var birth: String = "11.01.2002",
    var phone: String,
    var photo: String?,
    var id: UUID = UUID.randomUUID(),
)
