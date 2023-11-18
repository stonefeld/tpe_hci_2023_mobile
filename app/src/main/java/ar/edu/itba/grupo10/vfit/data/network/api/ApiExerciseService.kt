package ar.edu.itba.grupo10.vfit.data.network.api

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkExercise
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiExerciseService {

    @GET("exercises")
    suspend fun getExercises(): Response<NetworkPagedContent<NetworkExercise>>

    @GET("exercise/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: Int): Response<NetworkExercise>

    @POST("exercise")
    suspend fun createExercise(@Body exercise: NetworkExercise): Response<NetworkExercise>

    @PUT("exercise/{exerciseId}")
    suspend fun modifyExercise(
        @Path("exerciseId") exerciseId: Int,
        exercise: NetworkExercise
    ): Response<NetworkExercise>

    @DELETE("exercise/{exerciseId}")
    suspend fun deleteExercise(@Path("exerciseId") exerciseId: Int): Response<NetworkExercise>

}