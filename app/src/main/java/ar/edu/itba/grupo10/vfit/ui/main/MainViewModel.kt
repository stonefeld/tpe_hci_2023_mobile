package ar.edu.itba.grupo10.vfit.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.grupo10.vfit.data.repository.UserRepository
import ar.edu.itba.grupo10.vfit.utils.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
//    private val routinesRepository: RoutineRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUIState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun login(username: String, password: String) = viewModelScope.launch {
        uiState = uiState.copy(
            isLoading = true,
            message = null
        )
        runCatching {
            userRepository.login(username, password)
        }.onSuccess {
            uiState = uiState.copy(
                isLoading = false,
                isAuthenticated = true
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isLoading = false
            )
        }
    }

    fun logout() = viewModelScope.launch {
        uiState = uiState.copy(
            isLoading = true,
            message = null
        )
        runCatching {
            userRepository.logout()
        }.onSuccess {
            uiState = uiState.copy(
                isLoading = false,
                isAuthenticated = false,
                currentUser = null,
//                routines = null
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isLoading = false
            )
        }
    }

    fun register(username: String, email: String, password: String) = viewModelScope.launch {
        uiState = uiState.copy(
            isLoading = true,
            message = null
        )
        runCatching {
            userRepository.register(username, email, password)
        }.onSuccess {
            uiState = uiState.copy(
                isLoading = false,
                isAuthenticated = true
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isLoading = false
            )
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        uiState = uiState.copy(
            isLoading = true,
            message = null
        )
        runCatching {
            userRepository.getCurrentUser(uiState.currentUser == null)
        }.onSuccess { response ->
            uiState = uiState.copy(
                isLoading = false,
                currentUser = response
            )
        }.onFailure { e ->
            uiState = uiState.copy(
                message = e.message,
                isLoading = false
            )
        }
    }

//    fun getRoutines() = viewModelScope.launch {
//        uiState = uiState.copy(
//            isLoading = true,
//            message = null
//        )
//        runCatching {
//            userRepository.getRoutines(true)
//        }.onSuccess { response ->
//            uiState = uiState.copy(
//                isLoading = false,
//                routines = response
//            )
//        }.onFailure { e ->
//            uiState = uiState.copy(
//                message = e.message,
//                isLoading = false
//            )
//        }
//    }

}