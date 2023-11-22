package ar.edu.itba.grupo10.vfit.data.models

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkMetadata
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRoutine
import java.util.Date

data class Routine(
    var id: Int?,
    var name: String,
    var detail: String,
    var difficulty: String,
    var isPublic: Boolean,
    var user: User?,
    var date: Date?,
    var score: Int?,
    var metadata: NetworkMetadata?
) {

    fun asNetworkModel(): NetworkRoutine {
        return NetworkRoutine(
            id = id,
            name = name,
            detail = detail,
            difficulty = difficulty,
            isPublic = isPublic,
            user = user,
            date = date,
            score = score,
            metadata = metadata
        )
    }

}