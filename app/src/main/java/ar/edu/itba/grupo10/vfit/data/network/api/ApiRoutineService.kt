package ar.edu.itba.grupo10.vfit.data.network.api

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkPagedContent
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkReview
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRoutine
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiRoutineService {

    @GET("routines?size=100")
    suspend fun getRoutines(@QueryMap args: Map<String, String>): Response<NetworkPagedContent<NetworkRoutine>>

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

    @GET("users/current/routines?size=100")
    suspend fun getMyRoutines(@QueryMap args: Map<String, String>): Response<NetworkPagedContent<NetworkRoutine>>

    @GET("favourites?size=100")
    suspend fun getFavorites(): Response<NetworkPagedContent<NetworkRoutine>>

    @POST("favourites/{routineId}")
    suspend fun addFavorite(@Path("routineId") routineId: Int): Response<Unit>

    @DELETE("favourites/{routineId}")
    suspend fun removeFavorite(@Path("routineId") routineId: Int): Response<Unit>

    @POST("reviews/{routineId}")
    suspend fun reviewRoutine(
        @Path("routineId") routineId: Int,
        @Body review: NetworkReview
    ): Response<NetworkReview>

}