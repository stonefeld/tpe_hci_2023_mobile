package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.components.TopBar
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@Composable
fun SettingsScreen(
    @StringRes text: Int,
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Row {
                TopBar(
                    text = text
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 10.dp)
                            .fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = stringResource(R.string.color_mode),
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))

                        var checked by remember { mutableStateOf(true) }
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            }
                        )

                    }
                    Divider(Modifier.padding(horizontal = 10.dp))
                    Row (
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                            .fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = stringResource(R.string.detailed_routine),
                        )

                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        var checked by remember { mutableStateOf(true) }
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, locale = "es")
@Composable
fun SettingsPreview() {
    VFitTheme {
        SettingsScreen(
            text = R.string.settings
        )
    }
}