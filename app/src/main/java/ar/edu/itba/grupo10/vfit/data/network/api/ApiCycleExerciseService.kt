package ar.edu.itba.grupo10.vfit.data.network.api

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCycleExercise
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiCycleExerciseService {

    @GET("cycles/{cycleId}/exercises?size=100")
    suspend fun getCycleExercises(@Path("cycleId") cycleId: Int): Response<NetworkPagedContent<NetworkCycleExercise>>

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun getCycleExercise(
        @Path("cycleId") cycleId: Int,
        @Path("exerciseId") exerciseId: Int
    ): Response<NetworkCycleExercise>

    @POST("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun createCycleExercise(
        @Path("cycleId") cycleId: Int,
        @Path("exerciseId") exerciseId: Int,
        @Body cycleExercise: NetworkCycleExercise
    ): Response<NetworkCycleExercise>

    @PUT("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun modifyCycleExercise(
        @Path("cycleId") cycleId: Int,
        @Path("exerciseId") exerciseId: Int,
        @Body cycleExercise: NetworkCycleExercise
    ): Response<NetworkCycleExercise>

    @DELETE("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun deleteCycleExercise(
        @Path("cycleId") cycleId: Int,
        @Path("exerciseId") exerciseId: Int
    ): Response<Unit>

}