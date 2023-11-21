package ar.edu.itba.grupo10.vfit.data.models

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkExercise
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkMetadata

data class Exercise(
    var id: Int?,
    var name: String,
    var detail: String,
    var type: String,
    var metadata: NetworkMetadata?
) {

    fun asNetworkModel(): NetworkExercise {
        return NetworkExercise(
            id = id,
            name = name,
            detail = detail,
            type = type
        )
    }

}
