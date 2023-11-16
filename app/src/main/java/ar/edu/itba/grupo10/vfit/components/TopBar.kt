package ar.edu.itba.grupo10.vfit.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = colorScheme.primary
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(5.dp)
        ) {
            Column {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
            Column {
                TopAppBar(
                    title = {
                        Text(text = stringResource(text))
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorScheme.primary,
                        titleContentColor = colorScheme.onPrimary
                    ),
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FruitSaladTopAppBarPreview() {
    VFitTheme {
        TopBar(
            text = R.string.app_name
        )
    }
}