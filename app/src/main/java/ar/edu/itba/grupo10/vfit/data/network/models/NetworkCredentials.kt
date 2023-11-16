package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkCredentials(
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)
