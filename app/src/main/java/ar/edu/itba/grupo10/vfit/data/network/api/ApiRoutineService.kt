package ar.edu.itba.grupo10.vfit.data.network.api

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRoutine
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiRoutineService {

    @GET("routines")
    suspend fun getRoutines(): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") routineId: Int): Response<NetworkRoutine>

    @POST("routines")
    suspend fun createRoutine(@Body routine: NetworkRoutine): Response<NetworkRoutine>

    @PUT("routines/{routineId}")
    suspend fun modifyRoutine(
        @Path("routineId") routineId: Int,
        @Body routine: NetworkRoutine
    ): Response<NetworkRoutine>

    @DELETE("routines/{routineId}")
    suspend fun deleteRoutine(@Path("routineId") routineId: Int): Response<NetworkRoutine>

}