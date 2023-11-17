package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkRegisterCredentials(
    @SerializedName("username") var username: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)
