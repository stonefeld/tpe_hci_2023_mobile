package ar.edu.itba.grupo10.vfit.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.screens.ExecuteRoutineScreen
import ar.edu.itba.grupo10.vfit.ui.screens.RoutineScreen
import ar.edu.itba.grupo10.vfit.ui.screens.LibraryScreen
import ar.edu.itba.grupo10.vfit.ui.screens.HomeScreen
import ar.edu.itba.grupo10.vfit.ui.screens.LoginScreen
import ar.edu.itba.grupo10.vfit.ui.screens.ProfileScreen
import ar.edu.itba.grupo10.vfit.ui.screens.RegisterScreen
import ar.edu.itba.grupo10.vfit.ui.screens.SearchScreen
import ar.edu.itba.grupo10.vfit.ui.screens.SettingsScreen
import ar.edu.itba.grupo10.vfit.ui.screens.VerifyAccountScreen

sealed class Screen(val route: String, @StringRes val resourceId: Int?, val icon: ImageVector?) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Routine : Screen("routine/{routine_id}", R.string.routines, Icons.Default.FitnessCenter)
    object Search : Screen("search", R.string.search, Icons.Default.Search)

    object ExecuteRoutine : Screen("routine/{routine_id}/execute", R.string.execute, Icons.Default.FitnessCenter)
    object Profile : Screen("profile", R.string.profile, Icons.Default.Person)
    object Settings : Screen("settings", R.string.settings, Icons.Default.Settings)

    object Login : Screen("login", null, null)
    object Register : Screen("register", null, null)
    object Verify : Screen("verify", null, null)
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main",
    appState: MainAppState
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        /* main */
        mainGraph(navController, appState)

        /* auth */
        loginGraph(navController, appState)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController, appState: MainAppState) {
    navigation(startDestination = Screen.Home.route, route = "main") {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Routine.route,
            arguments = listOf(
                navArgument(name = "routine_id") {
                    type = NavType.IntType
                }
            )
        ) { backstackEntry ->
            RoutineScreen(
                navController,
                routineID = backstackEntry.arguments?.getInt("routine_id"),
            )
        }
        composable(Screen.Search.route) { SearchScreen(navController) }
        //composable(Screen.ExecuteRoutine.route) { ExecuteRoutineScreen(true) }
        composable(Screen.ExecuteRoutine.route,
            arguments = listOf(
                navArgument(name = "routine_id") {
                    type = NavType.IntType
                }
            )
        ) { backstackEntry ->
            ExecuteRoutineScreen(
                navController,
                routineID = backstackEntry.arguments?.getInt("routine_id"),
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen {
                navController.navigate("auth") {
                    popUpTo("main") {
                        inclusive = true
                    }
                }
            }
        }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}

fun NavGraphBuilder.loginGraph(navController: NavHostController, appState: MainAppState) {
    navigation(startDestination = Screen.Login.route, route = "auth") {
        composable(Screen.Login.route) {
            LoginScreen(navController, appState = appState,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    }
                })
        }
        composable(Screen.Register.route) {
            RegisterScreen(appState = appState, onRegisterSuccess = {
                navController.navigate(Screen.Verify.route)
            })
        }
        composable(Screen.Verify.route) {
            VerifyAccountScreen(appState = appState,
                onVerifySuccess = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}