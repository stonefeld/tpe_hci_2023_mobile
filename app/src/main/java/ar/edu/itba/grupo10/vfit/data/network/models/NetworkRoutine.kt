package ar.edu.itba.grupo10.vfit.data.network.models

import ar.edu.itba.grupo10.vfit.data.models.Routine
import com.google.gson.annotations.SerializedName
import java.util.Date

data class NetworkRoutine(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String,
    @SerializedName("detail") var detail: String,
    @SerializedName("difficulty") var difficulty: String,
    @SerializedName("isPublic") var isPublic: Boolean,
    @SerializedName("date") var date: Date? = null,
    @SerializedName("score") var score: Int? = null,
    @SerializedName("category") var category: List<Unit> = arrayListOf()
) {

    fun asModel(): Routine {
        return Routine(
            id = id,
            name = name,
            detail = detail,
            difficulty = difficulty,
            isPublic = isPublic
        )
    }

}