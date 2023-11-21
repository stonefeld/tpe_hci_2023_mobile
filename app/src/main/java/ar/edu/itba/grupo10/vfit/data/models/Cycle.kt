package ar.edu.itba.grupo10.vfit.data.models

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCycle

data class Cycle(
    var id: Int?,
    var name: String,
    var detail: String,
    var type: String,
    var order: Int,
    var repetitions: Int,
    var exercises: List<CycleExercise>? = null
) {

    fun asNetworkModel(): NetworkCycle {
        return NetworkCycle(
            id = id,
            name = name,
            detail = detail,
            type = type,
            order = order,
            repetitions = repetitions
        )
    }

}