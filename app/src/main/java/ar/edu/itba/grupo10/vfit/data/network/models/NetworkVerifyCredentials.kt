package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkVerifyCredentials(
    @SerializedName("email") var email: String,
    @SerializedName("code") var code: String
)
