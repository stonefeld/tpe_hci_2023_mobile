package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkSupport(
    @SerializedName("url") var url: String,
    @SerializedName("text") var text: String
)
