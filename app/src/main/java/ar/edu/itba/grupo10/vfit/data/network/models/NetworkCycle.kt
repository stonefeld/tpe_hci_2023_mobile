package ar.edu.itba.grupo10.vfit.data.network.models

import ar.edu.itba.grupo10.vfit.data.models.Cycle
import com.google.gson.annotations.SerializedName

data class NetworkCycle(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String,
    @SerializedName("detail") var detail: String,
    @SerializedName("type") var type: String,
    @SerializedName("order") var order: Int,
    @SerializedName("repetitions") var repetitions: Int
) {

    fun asModel(): Cycle {
        return Cycle(
            id = id,
            name = name,
            detail = detail,
            type = type,
            order = order,
            repetitions = repetitions
        )
    }

}