package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
) {
    Surface {
        Column(
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
                modifier= Modifier.padding(5.dp)
            ){
                Column (
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
                    //TODO: que sea un boton a la ejecucion de la rutina
                    Icon(
                        Icons.Rounded.PlayCircleOutline,
                        contentDescription = stringResource(id = R.string.enter_mail),
                        modifier = Modifier.size(60.dp),

                    )
                }
            }

            Column(
                modifier = Modifier.padding(10.dp)
            ){

                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    border = BorderStroke(1.dp, Color.Blue),
                    modifier = Modifier
                        .width(width = 390.dp)
                ) {
                    Text(
                        text = "Ciclo 1",
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .width(380.dp)
                            .padding(5.dp)
                    ) {
                        // TODO: que no sea tan bostero y poner posiciones relativas
                        Row(){
                            Text(
                                text = "Exer 1",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                        Row(){
                            Text(
                                text = "Exer 2",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                        Row(){
                            Text(
                                text = "Exer 3",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                    }


                }

                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    border = BorderStroke(1.dp, Color.Blue),
                    modifier = Modifier
                        .width(width = 390.dp)
                ) {
                    Text(
                        text = "Ciclo 2",
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .width(380.dp)
                            .padding(5.dp)
                    ) {
                        // TODO: que no sea tan bostero y poner posiciones relativas
                        Row(){
                            Text(
                                text = "Exer 1",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                        Row(){
                            Text(
                                text = "Exer 2",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                        Row(){
                            Text(
                                text = "Exer 3",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                    }


                }

                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    border = BorderStroke(1.dp, Color.Blue),
                    modifier = Modifier
                        .width(width = 390.dp)
                ) {
                    Text(
                        text = "Ciclo 3",
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .width(380.dp)
                            .padding(5.dp)
                    ) {
                        // TODO: que no sea tan bostero y poner posiciones relativas
                        Row(){
                            Text(
                                text = "Exer 1",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                        Row(){
                            Text(
                                text = "Exer 2",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                        Row(){
                            Text(
                                text = "Exer 3",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = "                                  ",
//                                modifier = Modifier
//                                    .padding(90.dp),
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "3x25",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                            Text(
                                text = "60\"",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.End,
                            )
                        }
                    }


                }


            }





        }
    }
}


@Preview(showSystemUi = true, locale = "es")
@Composable
fun RoutineScreenPreview() {
    VFitTheme {
        RoutineScreen()
    }
}