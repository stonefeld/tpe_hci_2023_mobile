package ar.edu.itba.grupo10.vfit.ui.main

import android.location.SettingInjectorService
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.grupo10.vfit.ui.screens.ExecuteRoutineScreen
import ar.edu.itba.grupo10.vfit.ui.screens.HomeScreen
import ar.edu.itba.grupo10.vfit.ui.screens.LoginScreen
import ar.edu.itba.grupo10.vfit.ui.screens.RegisterScreen
import ar.edu.itba.grupo10.vfit.ui.screens.ProfileScreen
import ar.edu.itba.grupo10.vfit.ui.screens.SettingsScreen
import ar.edu.itba.grupo10.vfit.ui.screens.VerifyAcountScreen


@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen() }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen() }
        composable("execute_routine") { ExecuteRoutineScreen() }
        composable("settings") { SettingsScreen(R.string.settings) }
        composable("register") { VerifyAcountScreen() }
        composable("register") { ProfileScreen() }
    }
}