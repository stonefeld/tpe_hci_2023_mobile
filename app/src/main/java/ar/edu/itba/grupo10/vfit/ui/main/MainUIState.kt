package ar.edu.itba.grupo10.vfit.ui.main

import ar.edu.itba.grupo10.vfit.data.models.Cycle
import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.data.models.Error
import ar.edu.itba.grupo10.vfit.data.models.Exercise
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User

data class MainUIState(
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val currentRoutine: Routine? = null,
    val routines: List<Routine>? = null,
    val myRoutines: List<Routine>? = null,
    val favorites: List<Routine>? = null,
    val currentCycle: Cycle? = null,
    val cycles: List<Cycle>? = null,
    val currentExercise: Exercise? = null,
    val exercises: List<Exercise>? = null,
    val currentCycleExercise: CycleExercise? = null,
    val cycleExercises: List<CycleExercise>? = null,
    val error: Error? = null,
)