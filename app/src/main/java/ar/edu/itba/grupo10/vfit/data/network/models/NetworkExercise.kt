package ar.edu.itba.grupo10.vfit.data.network.models

import ar.edu.itba.grupo10.vfit.data.models.Exercise
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkExercise(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String,
    @SerializedName("detail") var detail: String,
    @SerializedName("type") var type: String,
    @SerializedName("date") var date: Date? = null
) {

    fun asModel(): Exercise {
        return Exercise(
            id = id,
            name = name,
            detail = detail,
            type = type
        )
    }

}
