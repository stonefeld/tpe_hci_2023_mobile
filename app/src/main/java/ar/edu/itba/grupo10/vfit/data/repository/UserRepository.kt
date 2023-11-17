package ar.edu.itba.grupo10.vfit.data.repository

import ar.edu.itba.grupo10.vfit.data.models.User
import ar.edu.itba.grupo10.vfit.data.network.UserRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {

    private val currentUserMutex = Mutex()
    private var currentUser: User? = null

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun register(username: String, email: String, password: String) {
        remoteDataSource.register(username, email, password)
    }

    suspend fun getCurrentUser(refresh: Boolean): User? {
        if (refresh || currentUser == null) {
            val result = remoteDataSource.getCurrentUser()
            currentUserMutex.withLock {
                this.currentUser = result.asModel()
            }
        }
        return currentUserMutex.withLock { this.currentUser }
    }

}