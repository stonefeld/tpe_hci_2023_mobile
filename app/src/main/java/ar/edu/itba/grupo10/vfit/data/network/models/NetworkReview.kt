package ar.edu.itba.grupo10.vfit.data.network.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkReview(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("date") var date: Date? = null,
    @SerializedName("score") var score: Int,
    @SerializedName("review") var review: String
)
