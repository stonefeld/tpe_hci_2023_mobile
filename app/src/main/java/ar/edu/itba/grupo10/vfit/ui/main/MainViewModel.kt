package ar.edu.itba.grupo10.vfit.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.itba.grupo10.vfit.data.DataSourceException
import ar.edu.itba.grupo10.vfit.data.models.Cycle
import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.data.models.Error
import ar.edu.itba.grupo10.vfit.data.models.Exercise
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.repository.CycleExerciseRepository
import ar.edu.itba.grupo10.vfit.data.repository.CycleRepository
import ar.edu.itba.grupo10.vfit.data.repository.ExerciseRepository
import ar.edu.itba.grupo10.vfit.data.repository.RoutineRepository
import ar.edu.itba.grupo10.vfit.data.repository.UserRepository
import ar.edu.itba.grupo10.vfit.utils.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val routineRepository: RoutineRepository,
    private val cycleRepository: CycleRepository,
    private val exerciseRepository: ExerciseRepository,
    private val cycleExerciseRepository: CycleExerciseRepository
) : ViewModel() {

    var uiState by mutableStateOf(MainUIState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun dismissMessage() {
        uiState = uiState.copy(error = null)
    }

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
                currentRoutine = null,
                routines = null,
                myRoutines = null,
                favorites = null,
                currentCycle = null,
                cycles = null,
                currentExercise = null,
                exercises = null,
                currentCycleExercise = null,
                cycleExercises = null
            )
        },
        onSuccess
    )

    fun register(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        gender: String,
        avatarUrl: String,
        onSuccess: () -> Unit
    ) =
        runOnViewModelScope(
            {
                userRepository.register(
                    username,
                    email,
                    password,
                    firstName,
                    lastName,
                    phone,
                    gender,
                    avatarUrl
                )
            },
            { state, _ -> state },
            onSuccess
        )

    fun verifyAccount(email: String, code: String, onSuccess: () -> Unit) = runOnViewModelScope(
        { userRepository.verifyAccount(email, code) },
        { state, _ -> state },
        onSuccess
    )

    fun resendVerificationCode(email: String) = runOnViewModelScope(
        { userRepository.resendVerificationCode(email) },
        { state, _ -> state }
    )

    fun getCurrentUser(refresh: Boolean = false) = runOnViewModelScope(
        { userRepository.getCurrentUser(refresh || uiState.currentUser == null) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun modifyCurrentUser(
        firstName: String,
        lastName: String,
        phone: String,
        gender: String,
        avatarUrl: String,
        onModifySuccess: () -> Unit
    ) = runOnViewModelScope(
        { userRepository.modifyCurrentUser(firstName, lastName, phone, gender, avatarUrl) },
        { state, response -> state.copy(currentUser = response) },
        onModifySuccess
    )

    fun getRoutines(args: Map<String, String> = mapOf("direction" to "desc")) = runOnViewModelScope(
        { routineRepository.getRoutines(true, args) },
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

    fun getMyRoutines(args: Map<String, String> = mapOf("direction" to "desc")) =
        runOnViewModelScope(
            { routineRepository.getMyRoutines(true, args) },
            { state, response -> state.copy(myRoutines = response) }
        )

    fun getFavorites() = runOnViewModelScope(
        { routineRepository.getFavorites(true) },
        { state, response -> state.copy(favorites = response) }
    )

    fun addFavorite(routineId: Int) = runOnViewModelScope(
        { routineRepository.addFavorite(routineId) },
        { state, _ -> state.copy(favorites = null) },
        { getFavorites() }
    )

    fun removeFavorite(routineId: Int) = runOnViewModelScope(
        { routineRepository.removeFavorite(routineId) },
        { state, _ -> state.copy(favorites = null) },
        { getFavorites() }
    )

    fun reviewRoutine(routineId: Int, score: Int, onSuccess: () -> Unit) = runOnViewModelScope(
        { routineRepository.reviewRoutine(routineId, score) },
        { state, _ -> state.copy(currentRoutine = null, routines = null) },
        {
            onSuccess()
            getRoutine(routineId)
        }
    )

    fun getCycles(routineId: Int) = runOnViewModelScope(
        { cycleRepository.getCycles(true, routineId) },
        { state, response -> state.copy(cycles = response) }
    )

    fun getCyclesFull(routineId: Int) = runOnViewModelScope(
        { cycleRepository.getCyclesFull(true, routineId) },
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

    fun getExercises() = runOnViewModelScope(
        { exerciseRepository.getExercises(true) },
        { state, response -> state.copy(exercises = response) }
    )

    fun getExercise(routineId: Int) = runOnViewModelScope(
        { exerciseRepository.getExercise(routineId) },
        { state, response -> state.copy(currentExercise = response) }
    )

    fun addOrModifyExercise(exercise: Exercise) = runOnViewModelScope(
        {
            if (exercise.id == null)
                exerciseRepository.createExercise(exercise)
            else
                exerciseRepository.modifyExercise(exercise)
        },
        { state, response ->
            state.copy(
                currentExercise = response,
                routines = null
            )
        }
    )

    fun deleteExercise(exerciseId: Int) = runOnViewModelScope(
        { exerciseRepository.deleteExercise(exerciseId) },
        { state, _ ->
            state.copy(
                currentExercise = null,
                exercises = null
            )
        }
    )

    fun getCycleExercises(cycleId: Int) = runOnViewModelScope(
        { cycleExerciseRepository.getCycleExercises(true, cycleId) },
        { state, response -> state.copy(cycleExercises = response) }
    )

    fun getCycleExercise(cycleId: Int, exerciseId: Int) = runOnViewModelScope(
        { cycleExerciseRepository.getCycleExercise(cycleId, exerciseId) },
        { state, response -> state.copy(currentCycleExercise = response) }
    )

    fun addOrModifyCycleExercise(cycleId: Int, cycleExercise: CycleExercise) = runOnViewModelScope(
        {
            if (cycleExercise.exercise?.id == null)
                cycleExerciseRepository.createCycleExercise(cycleId, cycleExercise)
            else
                cycleExerciseRepository.modifyCycleExercise(cycleId, cycleExercise)
        },
        { state, response ->
            state.copy(
                currentCycleExercise = response,
                cycleExercises = null
            )
        }
    )

    fun deleteCycleExercise(cycleId: Int, exerciseId: Int) = runOnViewModelScope(
        { cycleExerciseRepository.deleteCycleExercise(cycleId, exerciseId) },
        { state, _ ->
            state.copy(
                currentCycleExercise = null,
                cycleExercises = null
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