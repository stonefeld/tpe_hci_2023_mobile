package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.itba.grupo10.vfit.R

@Composable
fun NavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.home))
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == "home"
            } == true,
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.routines))
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == "routines"
            } == true,
            onClick = {
                navController.navigate("routines") {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.profile))
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == "profile"
            } == true,
            onClick = {
                navController.navigate("profile") {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.settings))
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == "settings"
            } == true,
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BottomNavigationPreview() {
//    val largePadding = dimensionResource(R.dimen.large_padding)
//
//    VFitTheme {
//        NavigationBar(Modifier.padding(top = largePadding))
//    }
//}