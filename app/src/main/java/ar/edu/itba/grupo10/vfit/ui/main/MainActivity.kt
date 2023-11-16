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
import ar.edu.itba.grupo10.vfit.ui.components.NavigationBar
import ar.edu.itba.grupo10.vfit.ui.screens.HomeScreen
import ar.edu.itba.grupo10.vfit.ui.screens.LoginScreen
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VFitTheme {
                val viewModel: MainViewModel = viewModel(factory = getViewModelFactory())

                if (viewModel.uiState.isAuthenticated) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = { NavigationBar() }
                    ) { contentPadding ->
                        HomeScreen(
                            modifier = Modifier.padding(contentPadding),
                            viewModel = viewModel(),
                        )
                    }
                } else {
                    LoginScreen(viewModel = viewModel)
                }
            }
        }
    }

}