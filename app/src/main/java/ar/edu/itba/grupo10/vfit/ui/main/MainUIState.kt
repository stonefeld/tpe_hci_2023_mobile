package ar.edu.itba.grupo10.vfit.ui.main

import ar.edu.itba.grupo10.vfit.data.models.Error
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User

data class MainUIState(
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val currentRoutine: Routine? = null,
    val routines: List<Routine>? = null,
    val error: Error? = null
)