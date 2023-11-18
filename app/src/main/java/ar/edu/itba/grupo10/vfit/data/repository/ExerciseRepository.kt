package ar.edu.itba.grupo10.vfit.data.repository

import ar.edu.itba.grupo10.vfit.data.models.Exercise
import ar.edu.itba.grupo10.vfit.data.network.ExerciseRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ExerciseRepository(
    private val remoteDataSource: ExerciseRemoteDataSource
) {

    private val exercisesMutex = Mutex()
    private var exercises: List<Exercise> = emptyList()

    suspend fun getExercises(refresh: Boolean = false): List<Exercise> {
        if (refresh || exercises.isEmpty()) {
            val result = remoteDataSource.getExercises()
            exercisesMutex.withLock {
                this.exercises = result.content.map { it.asModel() }
            }
        }

        return exercisesMutex.withLock { this.exercises }
    }

    suspend fun getExercise(cycleId: Int): Exercise {
        return remoteDataSource.getExercise(cycleId).asModel()
    }

    suspend fun createExercise(exercise: Exercise): Exercise {
        val ret = remoteDataSource.createExercise(exercise.asNetworkModel()).asModel()
        exercisesMutex.withLock {
            this.exercises = emptyList()
        }
        return ret
    }

    suspend fun modifyExercise(exercise: Exercise): Exercise {
        val ret = remoteDataSource.modifyExercise(exercise.asNetworkModel()).asModel()
        exercisesMutex.withLock {
            this.exercises = emptyList()
        }
        return ret
    }

    suspend fun deleteExercise(exerciseId: Int) {
        remoteDataSource.deleteExercise(exerciseId)
        exercisesMutex.withLock {
            this.exercises = emptyList()
        }
    }

}