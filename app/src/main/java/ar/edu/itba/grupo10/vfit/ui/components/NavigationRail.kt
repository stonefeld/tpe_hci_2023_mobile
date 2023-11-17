package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme


@Composable
fun NavigationRail() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Profile", "Settings")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Settings)
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationRailPreview() {
    VFitTheme {
        NavigationRail()
    }
}