package ar.edu.itba.grupo10.vfit.ui.main

import ar.edu.itba.grupo10.vfit.data.models.User

data class MainUIState(
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val currentUser: User? = null,
//    val routines: List<Routines>? = null,
    val message: String? = null
)

val MainUIState.hasError: Boolean get() = message != null