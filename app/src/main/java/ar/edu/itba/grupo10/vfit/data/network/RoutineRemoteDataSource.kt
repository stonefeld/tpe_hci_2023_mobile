package ar.edu.itba.grupo10.vfit.data.network

import ar.edu.itba.grupo10.vfit.data.network.api.ApiRoutineService
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRoutine

class RoutineRemoteDataSource(
    private val apiRoutineService: ApiRoutineService
) : RemoteDataSource() {

    suspend fun getRoutines(args: Map<String, String>): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse { apiRoutineService.getRoutines(args) }
    }

    suspend fun getRoutine(routineId: Int): NetworkRoutine {
        return handleApiResponse { apiRoutineService.getRoutine(routineId) }
    }

    suspend fun createRoutine(routine: NetworkRoutine): NetworkRoutine {
        return handleApiResponse { apiRoutineService.createRoutine(routine) }
    }

    suspend fun modifyRoutine(routine: NetworkRoutine): NetworkRoutine {
        return handleApiResponse { apiRoutineService.modifyRoutine(routine.id!!, routine) }
    }

    suspend fun deleteRoutine(routineId: Int) {
        handleApiResponse { apiRoutineService.deleteRoutine(routineId) }
    }

    suspend fun getFavorites(): NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse { apiRoutineService.getFavorites() }
    }

    suspend fun addFavorite(routineId: Int) {
        handleApiResponse { apiRoutineService.addFavorite(routineId) }
    }

    suspend fun removeFavorite(routineId: Int) {
        handleApiResponse { apiRoutineService.removeFavorite(routineId) }
    }

}