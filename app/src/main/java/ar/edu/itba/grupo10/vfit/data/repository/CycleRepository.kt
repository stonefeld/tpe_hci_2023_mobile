package ar.edu.itba.grupo10.vfit.data.repository

import ar.edu.itba.grupo10.vfit.data.models.Cycle
import ar.edu.itba.grupo10.vfit.data.network.CycleRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CycleRepository(
    private val remoteDataSource: CycleRemoteDataSource
) {

    private val cyclesMutex = Mutex()
    private var cycles: List<Cycle> = emptyList()

    suspend fun getCycles(refresh: Boolean = false, routineId: Int): List<Cycle> {
        if (refresh || cycles.isEmpty()) {
            val result = remoteDataSource.getCycles(routineId)
            cyclesMutex.withLock {
                this.cycles = result.content.map { it.asModel() }
            }
        }

        return cyclesMutex.withLock { this.cycles }
    }

    suspend fun getCycle(routineId: Int, cycleId: Int): Cycle {
        return remoteDataSource.getCycle(routineId, cycleId).asModel()
    }

    suspend fun createCycle(routineId: Int, cycle: Cycle): Cycle {
        val ret = remoteDataSource.createCycle(routineId, cycle.asNetworkModel()).asModel()
        cyclesMutex.withLock {
            this.cycles = emptyList()
        }
        return ret
    }

    suspend fun modifyCycle(routineId: Int, cycle: Cycle): Cycle {
        val ret = remoteDataSource.modifyCycle(routineId, cycle.asNetworkModel()).asModel()
        cyclesMutex.withLock {
            this.cycles = emptyList()
        }
        return ret
    }

    suspend fun deleteCycle(routineId: Int, cycleId: Int) {
        remoteDataSource.deleteCycle(routineId, cycleId)
        cyclesMutex.withLock {
            this.cycles = emptyList()
        }
    }

}