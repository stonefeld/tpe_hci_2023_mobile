package ar.edu.itba.grupo10.vfit.data.models

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCycleExercise
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkExercise

data class CycleExercise(
    var order: Int,
    var duration: Int,
    var repetitions: Int,
    var exercise: NetworkExercise?
) {

    fun asNetworkModel(): NetworkCycleExercise {
        return NetworkCycleExercise(
            order = order,
            duration = duration,
            repetitions = repetitions,
            exercise = exercise
        )
    }

}
