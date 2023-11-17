package ar.edu.itba.grupo10.vfit.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.grupo10.vfit.ui.components.NavigationBar
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VFitTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
                val uiState = viewModel.uiState

                // TODO: el navigation bar esta medio xd
                Scaffold(
                    bottomBar = { NavigationBar(navController) }
                ) { innerPadding ->
                    MainNavHost(
                        navController,
                        startDestination = if (uiState.isAuthenticated) "main" else "auth",
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }

}