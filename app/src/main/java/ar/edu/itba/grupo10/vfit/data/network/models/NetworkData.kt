package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkData(
    @SerializedName("id") var id: Int,
    @SerializedName("email") var email: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("avatar") var avatar: String
)
