package ar.edu.itba.grupo10.vfit.data.network.models

import ar.edu.itba.grupo10.vfit.data.models.User
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkUser(
    @SerializedName("id") var id: Int?,
    @SerializedName("username") var username: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String = "",
    @SerializedName("avatarUrl") var avatarUrl: String = "",
    @SerializedName("metadata") var metadata: NetworkMetadata? = null,
    @SerializedName("verified") var verified: Boolean
) {

    fun asModel(): User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            avatarUrl = avatarUrl,
            phone = phone,
            gender = gender,
            verified = verified
        )
    }

}
