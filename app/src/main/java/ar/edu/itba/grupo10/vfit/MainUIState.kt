package ar.edu.itba.grupo10.vfit

import ar.edu.itba.grupo10.vfit.data.network.models.NetworkListUsers

data class MainUIState(
    val users: NetworkListUsers? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

val MainUIState.hasError: Boolean get() = message != null