package ar.edu.itba.grupo10.vfit.data.network

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkListUsers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/users")
    suspend fun getAllUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 50
    ): Response<NetworkListUsers>

}