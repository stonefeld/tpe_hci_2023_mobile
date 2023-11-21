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

    suspend fun register(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        gender: String,
        avatarUrl: String
    ) {
        remoteDataSource.register(username, email, password, firstName, lastName, phone, gender, avatarUrl)
    }

    suspend fun verifyAccount(email: String, code: String) {
        remoteDataSource.verifyAccount(email, code)
    }

    suspend fun resendVerificationCode(email: String) {
        remoteDataSource.resendVerificationCode(email)
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

    suspend fun modifyCurrentUser(
        firstName: String,
        lastName: String,
        phone: String,
        gender: String,
        avatarUrl: String
    ): User? {
        val user = currentUserMutex.withLock {
            this.currentUser?.copy(
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                gender = gender,
                avatarUrl = avatarUrl
            )
        }
        val result = remoteDataSource.modifyCurrentUser(user!!.asNetworkModel())
        currentUserMutex.withLock {
            this.currentUser = result.asModel()
        }
        return currentUserMutex.withLock { this.currentUser }
    }

}