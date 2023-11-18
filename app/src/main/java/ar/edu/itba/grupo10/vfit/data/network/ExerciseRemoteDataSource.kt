package ar.edu.itba.grupo10.vfit.data.network

import ar.edu.itba.grupo10.vfit.data.network.api.ApiExerciseService
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkExercise
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent

class ExerciseRemoteDataSource(
    private val apiExerciseService: ApiExerciseService
) : RemoteDataSource() {

    suspend fun getExercises(): NetworkPagedContent<NetworkExercise> {
        return handleApiResponse {
            apiExerciseService.getExercises()
        }
    }

    suspend fun getExercise(exerciseId: Int): NetworkExercise {
        return handleApiResponse {
            apiExerciseService.getExercise(exerciseId)
        }
    }

    suspend fun createExercise(exercise: NetworkExercise): NetworkExercise {
        return handleApiResponse {
            apiExerciseService.createExercise(exercise)
        }
    }

    suspend fun modifyExercise(exercise: NetworkExercise): NetworkExercise {
        return handleApiResponse {
            apiExerciseService.modifyExercise(exercise.id!!, exercise)
        }
    }

    suspend fun deleteExercise(exerciseId: Int) {
        handleApiResponse {
            apiExerciseService.deleteExercise(exerciseId)
        }
    }

}