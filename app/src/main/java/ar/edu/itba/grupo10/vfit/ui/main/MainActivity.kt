package ar.edu.itba.grupo10.vfit.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.grupo10.vfit.ui.components.NavigationBar
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VFitTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
                val uiState = viewModel.uiState
                val appState = rememberMainAppState()

                Scaffold(
                    bottomBar = { NavigationBar(navController, navBarVisibility(navController)) },
                    snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) },
                ) { contentPadding ->
                    MainNavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        navController,
                        startDestination = if (uiState.isAuthenticated) "main" else "auth",
                        appState
                    )
                }
            }
        }
    }
}

@Composable
fun navBarVisibility(
    navController: NavController
): MutableState<Boolean> {
    val state = rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screen.Home.route, Screen.Search.route, Screen.Profile.route -> state.value = true
        else -> state.value = false
    }

    return state
}