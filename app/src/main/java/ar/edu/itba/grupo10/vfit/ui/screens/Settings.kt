package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@Composable
fun SettingsScreen() {
    Surface {
        var detailedRoutine = rememberDetailedRoutine()
        val windowSize = rememberWindowInfo()
        Column(
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 5.dp)
                            .fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = stringResource(R.string.dark_mode),
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ) {
                            var checked by remember { mutableStateOf(true) }
                            Switch(
                                checked = checked,
                                onCheckedChange = {
                                    checked = it
                                }
                            )
                        }
                    }

                    Divider(Modifier.padding(horizontal = 20.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 5.dp)
                            .fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = stringResource(R.string.detailed_routine),
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ) {
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
}

@Composable
fun rememberDetailedRoutine(): Boolean {
    val detailedRoutine = false;
    return detailedRoutine
}

@Preview(showSystemUi = true, locale = "es")
@Composable
fun SettingsPreview() {
    VFitTheme {
        SettingsScreen()
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun SettingsPreview1() {
    VFitTheme {
        SettingsScreen(
        )
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
@Composable
fun SettingsPreview2() {
    VFitTheme {
        SettingsScreen(
        )
    }
}