package ar.edu.itba.grupo10.vfit.data.models

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkUser
import java.util.Date

data class User(
    var id: Int?,
    var username: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var gender: String?,
    var phone: String?,
    var birthdate: Date?,
    var avatarUrl: String? = null,
    var verified: Boolean
) {

    fun asNetworkModel(): NetworkUser {
        return NetworkUser(
            id = id,
            username = username,
            email = email,
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            phone = phone,
            birthdate = birthdate,
            avatarUrl = avatarUrl,
            verified = verified
        )
    }

}
