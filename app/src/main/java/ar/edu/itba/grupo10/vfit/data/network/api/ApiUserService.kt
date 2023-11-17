package ar.edu.itba.grupo10.vfit.data.network.api

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCredentials
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRegisterCredentials
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkToken
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUserService {

    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @POST("users")
    suspend fun register(@Body credentials: NetworkRegisterCredentials): Response<NetworkUser>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>

}