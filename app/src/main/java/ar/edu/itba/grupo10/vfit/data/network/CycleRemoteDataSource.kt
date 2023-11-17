package ar.edu.itba.grupo10.vfit.data.network

import ar.edu.itba.grupo10.vfit.data.network.api.ApiCycleService
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCycle
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent

class CycleRemoteDataSource(
    private val apiCycleService: ApiCycleService
) : RemoteDataSource() {

    suspend fun getCycles(routineId: Int): NetworkPagedContent<NetworkCycle> {
        return handleApiResponse {
            apiCycleService.getCycles(routineId)
        }
    }

    suspend fun getCycle(routineId: Int, cycleId: Int): NetworkCycle {
        return handleApiResponse {
            apiCycleService.getCycle(routineId, cycleId)
        }
    }

    suspend fun createCycle(routineId: Int, cycle: NetworkCycle): NetworkCycle {
        return handleApiResponse {
            apiCycleService.createCycle(routineId, cycle)
        }
    }

    suspend fun modifyCycle(routineId: Int, cycle: NetworkCycle): NetworkCycle {
        return handleApiResponse {
            apiCycleService.modifyCycle(routineId, cycle.id!!, cycle)
        }
    }

    suspend fun deleteCycle(routineId: Int, cycleId: Int) {
        handleApiResponse {
            apiCycleService.deleteCycle(routineId, cycleId)
        }
    }

}