package ar.edu.itba.grupo10.vfit.data.models

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRoutine

data class Routine(
    var id: Int?,
    var name: String,
    var detail: String,
    var difficulty: String,
    var isPublic: Boolean
) {

    fun asNetworkModel(): NetworkRoutine {
        return NetworkRoutine(
            id = id,
            name = name,
            detail = detail,
            difficulty = difficulty,
            isPublic = isPublic
        )
    }

}