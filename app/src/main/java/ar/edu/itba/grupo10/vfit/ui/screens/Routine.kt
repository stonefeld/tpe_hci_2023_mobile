package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
) {
    Surface {
        val windowSize = rememberWindowInfo()
        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.padding(35.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                    {
                        Text(
                            text = "Title",
                            fontSize = 40.sp
                        )
                        Text(
                            text = "Description Description Description Description Description Description Description",
                            fontSize = 15.sp
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {


                        Icon(
                            Icons.Rounded.Share,
                            contentDescription = stringResource(id = R.string.enter_mail),
                            modifier = Modifier
                                .size(30.dp)
                                .padding(vertical = 5.dp)
                        )

                        // TODO: que sea un boton a la ejecucion de la rutina
                        Icon(
                            Icons.Rounded.PlayCircleOutline,
                            contentDescription = stringResource(id = R.string.enter_mail),
                            modifier = Modifier.size(60.dp),
                        )
                    }
                }
                // Seria for de n/3 +1
                for (i in 1..(4 / 3 + 1)) {
                    Row {
                        Cycle(i)
                        Spacer(modifier = Modifier.padding(5.dp))
                        // TODO: hacer if si corresponde o no mostrar este
                        Cycle(i + 1)
                        Spacer(modifier = Modifier.padding(5.dp))
                        // TODO: hacer if si corresponde o no mostrar este
                        Cycle(i + 2)
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
                Spacer(modifier = Modifier.padding(50.dp))
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {

                Image(
                    painter = painterResource(id = R.drawable.routine_top),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(400.dp)
                )

                Row(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Column(
                        modifier = Modifier.width(300.dp)
                    )
                    {
                        Text(
                            text = "Title",
                            fontSize = 40.sp
                        )
                        Text(
                            text = "Description Description Description Description Description Description Description",
                            fontSize = 15.sp
                        )
                    }
                    Column(
                        modifier = Modifier.width(60.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {


                        Icon(
                            Icons.Rounded.Share,
                            contentDescription = stringResource(id = R.string.enter_mail),
                            modifier = Modifier
                                .size(30.dp)
                                .padding(vertical = 5.dp)
                        )

                        //TODO: que sea un boton a la ejecucion de la rutina
                        Icon(
                            Icons.Rounded.PlayCircleOutline,
                            contentDescription = stringResource(id = R.string.enter_mail),
                            modifier = Modifier.size(60.dp),

                            )
                    }
                }

                for (i in 1..3) {
                    Cycle(i)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    }
}

@Composable
fun Cycle(num: Int) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Blue),
        modifier = Modifier
            .width(width = 390.dp)
    ) {
        Text(
            text = "Ciclo $num",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            ),
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            // TODO: que no sea tan bostero y poner posiciones relativas

            for (i in 1..3) {
                Exercise("Excercise $i", "Series $i", "Time $i")
                Divider()
            }
        }
    }
}

@Composable
fun Exercise(name: String, series: String, time: String) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            modifier = Modifier
                .padding(vertical = 8.dp),
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = series,
            modifier = Modifier
                .padding(vertical = 8.dp),
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = "$time\"",
            modifier = Modifier
                .padding(vertical = 8.dp),
        )
    }
}


@Preview(showSystemUi = true, locale = "es", device = "spec:width=400dp,height=891dp")
@Composable
fun RoutineScreenPreview() {
    VFitTheme {
        RoutineScreen()
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun RoutineScreenPreview1() {
    VFitTheme {
        RoutineScreen()
    }
}
