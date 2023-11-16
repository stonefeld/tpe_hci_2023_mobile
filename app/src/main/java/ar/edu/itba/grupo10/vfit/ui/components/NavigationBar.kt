package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    NavigationBar (
        containerColor = colorScheme.primary,
        contentColor = colorScheme.onPrimary,
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
            selected = true,
            onClick = {  }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.routines))
            },
            selected = false,
            onClick = {}
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
            selected = false,
            onClick = {}
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
            selected = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    val largePadding = dimensionResource(R.dimen.large_padding)

    VFitTheme {
        NavigationBar(Modifier.padding(top = largePadding))
    }
}