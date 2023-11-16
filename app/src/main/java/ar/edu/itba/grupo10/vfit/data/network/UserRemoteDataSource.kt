package ar.edu.itba.grupo10.vfit.data.network

import ar.edu.itba.grupo10.vfit.data.network.api.ApiUserService
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkCredentials
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkUser
import ar.edu.itba.grupo10.vfit.utils.SessionManager

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val apiUserService: ApiUserService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            apiUserService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { apiUserService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser(): NetworkUser {
        return handleApiResponse { apiUserService.getCurrentUser() }
    }

}