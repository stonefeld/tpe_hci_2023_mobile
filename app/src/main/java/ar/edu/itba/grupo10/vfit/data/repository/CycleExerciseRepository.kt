package ar.edu.itba.grupo10.vfit.data.repository

import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.data.network.CycleExerciseRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CycleExerciseRepository(
    private val remoteDataSource: CycleExerciseRemoteDataSource
) {

    private val cycleExercisesMutex = Mutex()
    private var cycleExercises: List<CycleExercise> = emptyList()

    suspend fun getCycleExercises(refresh: Boolean = false, routineId: Int): List<CycleExercise> {
        if (refresh || cycleExercises.isEmpty()) {
            val result = remoteDataSource.getCycleExercises(routineId)
            cycleExercisesMutex.withLock {
                this.cycleExercises = result.content.map { it.asModel() }
            }
        }

        return cycleExercisesMutex.withLock { this.cycleExercises }
    }

    suspend fun getCycleExercise(cycleId: Int, exerciseId: Int): CycleExercise {
        return remoteDataSource.getCycleExercise(cycleId, exerciseId).asModel()
    }

    suspend fun createCycleExercise(cycleId: Int, cycleExercise: CycleExercise): CycleExercise {
        val ret =
            remoteDataSource.createCycleExercise(cycleId, cycleExercise.asNetworkModel()).asModel()
        cycleExercisesMutex.withLock {
            this.cycleExercises = emptyList()
        }
        return ret
    }

    suspend fun modifyCycleExercise(cycleId: Int, cycleExercise: CycleExercise): CycleExercise {
        val ret =
            remoteDataSource.modifyCycleExercise(cycleId, cycleExercise.asNetworkModel()).asModel()
        cycleExercisesMutex.withLock {
            this.cycleExercises = emptyList()
        }
        return ret
    }

    suspend fun deleteCycleExercise(cycleId: Int, exerciseId: Int) {
        remoteDataSource.deleteCycleExercise(cycleId, exerciseId)
        cycleExercisesMutex.withLock {
            this.cycleExercises = emptyList()
        }
    }

}