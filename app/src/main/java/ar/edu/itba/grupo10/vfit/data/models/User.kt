package ar.edu.itba.grupo10.vfit.data.models

import java.util.Date

data class User(
    var id: Int?,
    var username: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var avatarUrl: String? = null,
    var lastActivity: Date? = null
)
