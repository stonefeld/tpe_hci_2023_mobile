package ar.edu.itba.grupo10.vfit.utils

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import ar.edu.itba.grupo10.vfit.MainApplication

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null) : ViewModelFactory {
    val application = (LocalContext.current.applicationContext as MainApplication)
    val sessionManager = application.sessionManager
    val userRepository = application.userRepository
//    val routineRepository = application.routineRepository

    return ViewModelFactory(
        sessionManager,
        userRepository,
//        routineRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}