package ar.edu.itba.grupo10.vfit.data.network.models

import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import com.google.gson.annotations.SerializedName

data class NetworkCycleExercise(
    @SerializedName("order") var order: Int,
    @SerializedName("duration") var duration: Int,
    @SerializedName("repetitions") var repetitions: Int,
    @SerializedName("exercise") var exercise: NetworkExercise?
) {

    fun asModel(): CycleExercise {
        return CycleExercise(
            order = order,
            duration = duration,
            repetitions = repetitions,
            exercise = exercise
        )
    }

}
