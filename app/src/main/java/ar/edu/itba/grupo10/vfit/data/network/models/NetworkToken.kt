package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkToken(
    @SerializedName("token") var token: String
)
