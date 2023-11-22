package ar.edu.itba.grupo10.vfit.data.network

import ar.edu.itba.grupo10.vfit.data.network.api.ApiCycleExerciseService
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCycleExercise
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent

class CycleExerciseRemoteDataSource(
    private val apiCycleExerciseService: ApiCycleExerciseService
) : RemoteDataSource() {

    suspend fun getCycleExercises(cycleId: Int): NetworkPagedContent<NetworkCycleExercise> {
        return handleApiResponse {
            apiCycleExerciseService.getCycleExercises(cycleId)
        }
    }

    suspend fun getCycleExercise(cycleId: Int, exerciseId: Int): NetworkCycleExercise {
        return handleApiResponse {
            apiCycleExerciseService.getCycleExercise(cycleId, exerciseId)
        }
    }

    suspend fun createCycleExercise(
        cycleId: Int,
        cycleExercise: NetworkCycleExercise
    ): NetworkCycleExercise {
        return handleApiResponse {
            apiCycleExerciseService.createCycleExercise(
                cycleId,
                cycleExercise.exercise?.id!!,
                cycleExercise
            )
        }
    }

    suspend fun modifyCycleExercise(
        cycleId: Int,
        cycleExercise: NetworkCycleExercise
    ): NetworkCycleExercise {
        return handleApiResponse {
            apiCycleExerciseService.modifyCycleExercise(
                cycleId,
                cycleExercise.exercise?.id!!,
                cycleExercise
            )
        }
    }

    suspend fun deleteCycleExercise(cycleId: Int, exerciseId: Int) {
        handleApiResponse {
            apiCycleExerciseService.deleteCycleExercise(cycleId, exerciseId)
        }
    }

}