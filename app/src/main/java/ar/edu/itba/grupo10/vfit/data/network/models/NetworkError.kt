package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("code") var code: Int,
    @SerializedName("description") var description: String,
    @SerializedName("details") var details: List<String>? = null,
)
