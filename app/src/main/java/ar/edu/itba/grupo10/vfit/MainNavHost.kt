package ar.edu.itba.grupo10.vfit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import ar.edu.itba.grupo10.vfit.screens.HomeScreen
import ar.edu.itba.grupo10.vfit.screens.OtherScreen

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    val uri = "http://www.example.com"
    val secureUri = "https://www.example.com"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            HomeScreen(onNavigateToOtherScreen = { id -> navController.navigate("other/$id") })
        }
        composable(
            "other/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/other?id={id}" },
                navDeepLink { uriPattern = "$secureUri/other?id={id}" }
            )
        ) { route ->
            OtherScreen(route.arguments?.getInt("id"))
        }
    }
}