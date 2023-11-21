package ar.edu.itba.grupo10.vfit.data.network.api

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCredentials
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkRegisterCredentials
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkToken
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkUser
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkVerifyCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiUserService {

    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @POST("users")
    suspend fun register(@Body credentials: NetworkRegisterCredentials): Response<NetworkUser>

    @POST("users/verify_email")
    suspend fun verifyAccount(@Body credentials: NetworkVerifyCredentials): Response<Unit>

    @POST("users/resend_verification")
    suspend fun resendVerificationCode(@Body credentials: NetworkVerifyCredentials): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>

    @PUT("users/current")
    suspend fun modifyCurrentUser(@Body user: NetworkUser): Response<NetworkUser>

}