package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName

data class NetworkListUsers(
    @SerializedName("page") var page: Int,
    @SerializedName("per_page") var perPage: Int,
    @SerializedName("total") var total: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("data") var data: ArrayList<NetworkData> = arrayListOf(),
    @SerializedName("support") var support: NetworkSupport? = null
)
