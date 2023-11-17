package ar.edu.itba.grupo10.vfit.data.repository

import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.network.RoutineRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutineRepository(
    private val remoteDataSource: RoutineRemoteDataSource
) {

    private val routinesMutex = Mutex()
    private var routines: List<Routine> = emptyList()

    suspend fun getRoutines(refresh: Boolean = false): List<Routine> {
        if (refresh || routines.isEmpty()) {
            val result = remoteDataSource.getRoutines()
            routinesMutex.withLock {
                this.routines = result.content.map { it.asModel() }
            }
        }

        return routinesMutex.withLock { this.routines }
    }

    suspend fun getRoutine(routineId: Int): Routine {
        return remoteDataSource.getRoutine(routineId).asModel()
    }

    suspend fun createRoutine(routine: Routine): Routine {
        val ret = remoteDataSource.createRoutine(routine.asNetworkModel()).asModel()
        routinesMutex.withLock {
            this.routines = emptyList()
        }
        return ret
    }

    suspend fun modifyRoutine(routine: Routine): Routine {
        val ret = remoteDataSource.modifyRoutine(routine.asNetworkModel()).asModel()
        routinesMutex.withLock {
            this.routines = emptyList()
        }
        return ret
    }

    suspend fun deleteRoutine(routineId: Int) {
        remoteDataSource.deleteRoutine(routineId)
        routinesMutex.withLock {
            this.routines = emptyList()
        }
    }

}