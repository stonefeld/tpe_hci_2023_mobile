package ar.edu.itba.grupo10.vfit.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.grupo10.vfit.data.DataSourceException
import ar.edu.itba.grupo10.vfit.data.models.Cycle
import ar.edu.itba.grupo10.vfit.data.repository.RoutineRepository
import ar.edu.itba.grupo10.vfit.data.repository.UserRepository
import ar.edu.itba.grupo10.vfit.utils.SessionManager
import ar.edu.itba.grupo10.vfit.data.models.Error
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.repository.CycleRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val routineRepository: RoutineRepository,
    private val cycleRepository: CycleRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUIState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun login(username: String, password: String, onSuccess: () -> Unit) = runOnViewModelScope(
        { userRepository.login(username, password) },
        { state, _ -> state.copy(isAuthenticated = true) },
        onSuccess
    )

    fun logout(onSuccess: () -> Unit) = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                currentUser = null,
                routines = null,
            )
        },
        onSuccess
    )

    fun register(username: String, email: String, password: String) = runOnViewModelScope(
        { userRepository.register(username, email, password) },
        { state, _ -> state }
    )

    fun getCurrentUser() = runOnViewModelScope(
        { userRepository.getCurrentUser(uiState.currentUser == null) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun getRoutines() = runOnViewModelScope(
        { routineRepository.getRoutines(true) },
        { state, response -> state.copy(routines = response) }
    )

    fun getRoutine(routineId: Int) = runOnViewModelScope(
        { routineRepository.getRoutine(routineId) },
        { state, response -> state.copy(currentRoutine = response) }
    )

    fun addOrModifyRoutine(routine: Routine) = runOnViewModelScope(
        {
            if (routine.id == null)
                routineRepository.createRoutine(routine)
            else
                routineRepository.modifyRoutine(routine)
        },
        { state, response ->
            state.copy(
                currentRoutine = response,
                routines = null
            )
        }
    )

    fun deleteRoutine(routineId: Int) = runOnViewModelScope(
        { routineRepository.deleteRoutine(routineId) },
        { state, _ ->
            state.copy(
                currentRoutine = null,
                routines = null
            )
        }
    )

    fun getCycles(routineId: Int) = runOnViewModelScope(
        { cycleRepository.getCycles(true, routineId) },
        { state, response -> state.copy(cycles = response) }
    )

    fun getCycle(routineId: Int, cycleId: Int) = runOnViewModelScope(
        { cycleRepository.getCycle(routineId, cycleId) },
        { state, response -> state.copy(currentCycle = response) }
    )

    fun addOrModifyCycle(routineId: Int, cycle: Cycle) = runOnViewModelScope(
        {
            if (cycle.id == null)
                cycleRepository.createCycle(routineId, cycle)
            else
                cycleRepository.modifyCycle(routineId, cycle)
        },
        { state, response ->
            state.copy(
                currentCycle = response,
                cycles = null
            )
        }
    )

    fun deleteCycle(routineId: Int, cycleId: Int) = runOnViewModelScope(
        { cycleRepository.deleteCycle(routineId, cycleId) },
        { state, _ ->
            state.copy(
                currentCycle = null,
                cycles = null
            )
        }
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (MainUIState, R) -> MainUIState,
        onSuccess: () -> Unit = {}
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isLoading = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isLoading = false)
            onSuccess()
        }.onFailure { e ->
            uiState = uiState.copy(isLoading = false, error = handleError(e))
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }

}