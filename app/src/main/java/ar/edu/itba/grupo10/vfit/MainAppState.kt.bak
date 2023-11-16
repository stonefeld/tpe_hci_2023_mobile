package ar.edu.itba.grupo10.vfit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainAppState(
    val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState
) {

    fun showSnackbar(
        message: String,
        actionPerformed: () -> Unit,
        dismissed: () -> Unit
    ) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = MainApplication.instance.getString(R.string.dismiss)
            )

            when (result) {
                SnackbarResult.ActionPerformed -> {
                    actionPerformed()
                }
                SnackbarResult.Dismissed -> {
                    dismissed()
                }
            }
        }
    }

}

@Composable
fun rememberMyAppState(
    scope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(scope, snackbarHostState) {
    MainAppState(scope, snackbarHostState)
}