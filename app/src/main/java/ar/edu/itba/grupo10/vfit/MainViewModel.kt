package ar.edu.itba.grupo10.vfit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.grupo10.vfit.data.network.RetrofitClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var uiState by mutableStateOf(MainUIState())
        private set

    private var fetchJob: Job? = null

    fun dismissMessage() {
        uiState = uiState.copy(message = null)
    }

    fun fetchUsers(page: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            runCatching {
                var apiService = RetrofitClient.getApiService()
                apiService.getAllUsers(page)
            }.onSuccess { response ->
                uiState = uiState.copy(
                    users = response.body(),
                    isLoading = false
                )
            }.onFailure { e ->
                uiState = uiState.copy(
                    message = e.message,
                    isLoading = false
                )
            }
        }
    }

}