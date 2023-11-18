package ar.edu.itba.grupo10.vfit.data.models

import java.util.Date

data class User(
    var id: Int?,
    var username: String,
    var email: String,
    var firstName: String?,
    var lastName: String?,
    var avatarUrl: String? = null,
    var lastActivity: Date? = null
)
