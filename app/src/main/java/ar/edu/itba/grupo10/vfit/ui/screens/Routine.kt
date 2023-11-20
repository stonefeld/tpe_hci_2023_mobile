package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import java.util.Date

@Composable
fun RoutineCard(
    modifier: Modifier = Modifier,
    data: Routine
) {
    var liked by remember { mutableStateOf(false) }
    val windowSize = rememberWindowInfo()

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .heightIn(0.dp, 150.dp),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        FloatingActionButton(
                            modifier = Modifier.align(alignment = Alignment.TopStart),
                            onClick = {
                                // TODO
                            },
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                        if (liked) {
                            FloatingActionButton(
                                modifier = Modifier.align(alignment = Alignment.TopEnd),
                                onClick = {
                                    liked = false
                                },
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                )
                            }
                        } else {
                            FloatingActionButton(
                                modifier = Modifier.align(alignment = Alignment.TopEnd),
                                onClick = {
                                    liked = true
                                },
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
//                    AsyncImage(
//                    model = "",
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clip(RectangleShape)
//                )
                    Image(
                        painter = painterResource(id = R.drawable.exercise),
                        contentDescription = null,
                    )
                }
            }
            Divider(
                thickness = 5.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Row {
                Column(
                    modifier = Modifier
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxHeight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .padding(5.dp)
                        ) {
                            Text(
                                text = data.name,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                            Text(
                                text = stringResource(R.string.difficulty) + " " + stringResource(stringToRes(data.difficulty)),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                            Text(
                                text = data.date.toString(),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxHeight(1f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                                    .fillMaxSize(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.play),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .clickable {
                                                /*TODO:*/
                                            }
                                    )
                                    Icon(
                                        Icons.Rounded.Share,
                                        contentDescription = stringResource(id = R.string.enter_mail),
                                        modifier = Modifier
                                            .padding(vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .fillMaxWidth(1f)
                    ) {
                        Text(
                            text = data.detail,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            AddCycle()
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            AddCycle()
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            AddCycle()
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(0.5f)
                            ) {
                                AddCycle()
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                AddCycle()
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(0.5f)
                            ) {
                                AddCycle()
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                AddCycle()
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(0.5f)
                            ) {
                                AddCycle()
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                AddCycle()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddCycle() {
    Card(
        border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = "Ciclo",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        AddExerciseRoutine("Ejercicio 1", "4x10", "120''")
        AddExerciseRoutine("Ejercicio 2", "3x8", "180''")
        AddExerciseRoutine("Ejercicio 3", "4x12", "90''")
    }
}

@Composable
fun AddExerciseRoutine(
    exName: String,
    exReps: String,
    exTime: String,
) {
    Surface(
        color = Color(0xCCFFFFFF),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = exName,
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 4.dp,
                                end = 4.dp
                            )
                    ) {
                        Text(
                            text = exReps,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 4.dp,
                                end = 4.dp
                            )
                    ) {
                        Text(
                            text = exTime,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun RoutinePreview1() {
    VFitTheme {
        RoutineCard(
            Modifier, Routine(
                1,
                "LOREM",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
                "advanced",
                true,
                User(
                    1,
                    "paki",
                    "gmail",
                    "Paki",
                    "Quian",
                    null,
                    null,
                    null,
                    null,
                    true
                ),
                Date(2023, 10, 31),
            )
        )
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
@Composable
fun RoutinePreview2() {
    VFitTheme {
        RoutineCard(
            Modifier, Routine(
                1,
                "LOREM",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
                "advanced",
                true,
                User(
                    1,
                    "paki",
                    "gmail",
                    "Paki",
                    "Quian",
                    null,
                    null,
                    null,
                    null,
                    true
                ),
                Date(2023, 10, 31),
            )
        )
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun RoutinePreview3() {
    VFitTheme {
        RoutineCard(
            Modifier, Routine(
                1,
                "LOREM",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
                "advanced",
                true,
                User(
                    1,
                    "paki",
                    "gmail",
                    "Paki",
                    "Quian",
                    null,
                    null,
                    null,
                    null,
                    true
                ),
                Date(2023, 10, 31),
            )
        )
    }
}