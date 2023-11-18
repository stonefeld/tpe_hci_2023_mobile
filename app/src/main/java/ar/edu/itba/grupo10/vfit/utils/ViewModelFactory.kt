package ar.edu.itba.grupo10.vfit.utils

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ar.edu.itba.grupo10.vfit.data.repository.CycleRepository
import ar.edu.itba.grupo10.vfit.data.repository.ExerciseRepository
import ar.edu.itba.grupo10.vfit.data.repository.RoutineRepository
import ar.edu.itba.grupo10.vfit.data.repository.UserRepository
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val routineRepository: RoutineRepository,
    private val cycleRepository: CycleRepository,
    private val exerciseRepository: ExerciseRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                sessionManager,
                userRepository,
                routineRepository,
                cycleRepository,
                exerciseRepository
            )

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}