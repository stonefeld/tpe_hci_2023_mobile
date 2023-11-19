package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo

@Composable
fun ExecuteRoutineScreen(
    detailed: Boolean
) {
    val windowSize = rememberWindowInfo()
    var image = R.drawable.execute_routine_phone
    if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
        image = R.drawable.execute_routine_phone
    } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
        image = R.drawable.execute_routine_tablet
    }
    Box(
        modifier = with(Modifier) {
            fillMaxSize()
                .paint(
                    painterResource(id = image),
                    contentScale = ContentScale.FillBounds
                )
        })
    {
        Column {
            Surface(
                modifier = Modifier.fillMaxSize(1f),
                color = Color.Transparent,
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
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (detailed) {
                            Row(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.exercise),
                                    contentDescription = null,
                                    modifier = Modifier.size(230.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(
                                text = "Abdominales",
                                fontSize = 40.sp,
                                fontFamily = FontFamily.Default,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        Row(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(
                                text = "3 x 25 reps |  60’’",
                                fontSize = 35.sp,
                                fontFamily = FontFamily.Default,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                        if (!detailed) {
                            AddButtons()
                            if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded) {
                                AddNextExercise()
                            }
                        } else if (windowSize.screenWidthInfo != WindowInfo.WindowType.Medium) {
                            AddButtons()
                            AddNextExercise()
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (detailed) {
                            if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded) {
                                Surface(
                                    color = Color(0x55FFFFFF),
                                    shape = RoundedCornerShape(15),
                                    modifier = Modifier.padding(start = 50.dp)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                                    )
                                    {
                                        Column(
                                            modifier = Modifier.padding(20.dp),
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.25f)
                                            ) {
                                                Text(
                                                    text = "Ciclo 1",
                                                    fontSize = 30.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = FontFamily.Default,
                                                    color = MaterialTheme.colorScheme.background
                                                )
                                            }
                                            AddExercise("Ejercicio 1", "4x10", "120''")
                                            AddExercise("Ejercicio 2", "3x8", "180''")
                                            AddExercise("Ejercicio 3", "4x12", "90''")
                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.25f)
                                            ) {
                                                Text(
                                                    text = "Ciclo 2",
                                                    fontSize = 30.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = FontFamily.Default,
                                                    color = MaterialTheme.colorScheme.background
                                                )
                                            }
                                            AddExercise("Ejercicio 1", "4x10", "120''")
                                            AddExercise("Ejercicio 2", "3x8", "180''")
                                            AddExercise("Ejercicio 3", "4x12", "90''")
                                            Row(
                                                modifier = Modifier.fillMaxWidth(0.25f)
                                            ) {
                                                Text(
                                                    text = "Ciclo 3",
                                                    fontSize = 30.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = FontFamily.Default,
                                                    color = MaterialTheme.colorScheme.background
                                                )
                                            }
                                            AddExercise("Ejercicio 1", "4x10", "120''")
                                            AddExercise("Ejercicio 2", "3x8", "180''")
                                            AddExercise("Ejercicio 3", "4x12", "90''")
                                        }
                                    }
                                }
                            } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
                                AddButtons()
                                AddNextExercise()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddNextExercise() {
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            text = "Proximo:",
            fontSize = 25.sp,
            fontFamily = FontFamily.Default,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            text = "Flexiones de Brazo",
            fontSize = 25.sp,
            fontFamily = FontFamily.Default,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            text = "4 x 20 reps |  60’’",
            fontSize = 25.sp,
            fontFamily = FontFamily.Default,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun AddButtons() {
    var paused by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.previous),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )
        if (paused) {
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clickable { paused = false }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.pause),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clickable { paused = true }
            )
        }
        Image(
            painter = painterResource(id = R.drawable.skip),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )
    }
}

@Composable
fun AddExercise(
    exName: String,
    exReps: String,
    exTime: String,
) {
    Surface(
        color = Color(0xCCFFFFFF),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 10.dp,
                    end = 10.dp
                ),
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
fun PreviewExecuteRoutine1() {
    VFitTheme {
        ExecuteRoutineScreen(true)
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun PreviewExecuteRoutine2() {
    VFitTheme {
        ExecuteRoutineScreen(false)
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
@Composable
fun PreviewExecuteRoutine3() {
    VFitTheme {
        ExecuteRoutineScreen(true)
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
@Composable
fun PreviewExecuteRoutine4() {
    VFitTheme {
        ExecuteRoutineScreen(false)
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun PreviewExecuteRoutine5() {
    VFitTheme {
        ExecuteRoutineScreen(true)
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun PreviewExecuteRoutine6() {
    VFitTheme {
        ExecuteRoutineScreen(false)
    }
}