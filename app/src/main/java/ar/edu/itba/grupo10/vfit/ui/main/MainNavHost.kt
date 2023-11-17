package ar.edu.itba.grupo10.vfit.ui.main

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.screens.ExecuteRoutineScreen
import ar.edu.itba.grupo10.vfit.ui.screens.HomeScreen
import ar.edu.itba.grupo10.vfit.ui.screens.LoginScreen
import ar.edu.itba.grupo10.vfit.ui.screens.RegisterScreen
import ar.edu.itba.grupo10.vfit.ui.screens.ProfileScreen
import ar.edu.itba.grupo10.vfit.ui.screens.SettingsScreen
import ar.edu.itba.grupo10.vfit.ui.screens.VerifyAcountScreen

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object Routine : Screen("routine", R.string.routines)
    object Profile : Screen("profile", R.string.profile)
    object Settings : Screen("settings", R.string.settings)

    object Login : Screen("login", -1)
    object Register : Screen("register", -1)
    object Verify : Screen("verify", -1)
}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main",
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        /* main */
        mainGraph(navController)

        /* auth */
        loginGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(startDestination = "home", route = "main") {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable("execute_routine") { ExecuteRoutineScreen() }
        composable("settings") { SettingsScreen(R.string.settings) }
        composable("profile") {
            ProfileScreen(onLogoutSuccess = {
                navController.navigate("auth") {
                    popUpTo("main") {
                        inclusive = true
                    }
                }
            })
        }
    }
}

fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(startDestination = "login", route = "auth") {
        composable(Screen.Login.route) { LoginScreen(navController,
            onLoginSuccess = {
                navController.navigate("main") {
                    popUpTo("auth") { inclusive = true }
                }
            }) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Verify.route) { VerifyAcountScreen() }
    }
}