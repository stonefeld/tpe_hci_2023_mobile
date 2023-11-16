package ar.edu.itba.grupo10.vfit.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.components.TopBar
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@Composable
fun Settings(
    @StringRes text: Int,
) {
    Surface {
        Column {
            Row {
                TopBar(
                    text = text
                )
            }
            Row {

            }
        }
    }
}

@Preview(showSystemUi = true, locale = "es")
@Composable
fun SettingsPreview() {
    VFitTheme {
        Settings(
            text = R.string.settings
        )
    }
}