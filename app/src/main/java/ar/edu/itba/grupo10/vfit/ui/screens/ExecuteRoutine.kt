package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ZoomInMap
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import ar.edu.itba.grupo10.vfit.R
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

@Composable
fun ExecuteRoutineScreen(
    navController: NavHostController,
    routineID: Int?,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    if (routineID != null) {
        var detailed by rememberSaveable { mutableStateOf(false) }
        var finished by rememberSaveable { mutableStateOf(false) }
        val windowSize = rememberWindowInfo()
        var image = R.drawable.execute_routine_phone
        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
            image = R.drawable.execute_routine_phone
        } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
            image = R.drawable.execute_routine_tablet
        }

        OnLifeCycleEvent { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.getCyclesFull(routineID)
                }

                else -> {}
            }
        }

        if (viewModel.uiState.cycles != null) {
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
                                    navController.popBackStack()
                                },
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                            if (!finished) {
                                FloatingActionButton(
                                    modifier = Modifier.align(alignment = Alignment.TopEnd),
                                    onClick = {
                                        detailed = !detailed
                                    },
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = MaterialTheme.colorScheme.primary
                                ) {
                                    Icon(
                                        imageVector = if (detailed) Icons.Default.ZoomInMap else Icons.Default.ZoomOutMap,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            if (!finished) {
                                var currentCycleIndex by rememberSaveable { mutableIntStateOf(0) }
                                var currentExerciseIndex by rememberSaveable { mutableIntStateOf(0) }
                                val cycleSize = viewModel.uiState.cycles!!.size
                                //TODO: Si exerciseSize = 0 explota
                                val exerciseSize =
                                    viewModel.uiState.cycles!![currentCycleIndex].exercises?.size

                                val action: (inc: Int) -> Unit = { inc ->
                                    if (currentExerciseIndex + inc == exerciseSize) {
                                        currentCycleIndex += 1
                                        currentExerciseIndex = 0
                                        if (currentCycleIndex == cycleSize) {
                                            finished = true;
                                        }
                                    } else if (currentExerciseIndex + inc == -1) {
                                        currentCycleIndex -= 1
                                        if (exerciseSize != null) {
                                            currentExerciseIndex = exerciseSize - 1
                                        }
                                        if (currentCycleIndex == -1) {
                                            currentCycleIndex = 0
                                            currentExerciseIndex = 0
                                        }
                                    } else {
                                        currentExerciseIndex += inc
                                    }
                                }

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    if (detailed) {
                                        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .fillMaxHeight(0.5f)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.exercise),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(230.dp)
                                                )
                                            }
                                        } else {
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
                                    }
                                    Row(
                                        modifier = Modifier.padding(5.dp)
                                    ) {
                                        viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                            currentExerciseIndex
                                        )?.exercise?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 40.sp,
                                                fontFamily = FontFamily.Default,
                                                color = MaterialTheme.colorScheme.background
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.padding(5.dp)
                                    ) {
                                        viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                            currentExerciseIndex
                                        )?.let {
                                            Text(
                                                text = "${it.repetitions} reps |  ${it.duration}’’",
                                                fontSize = 35.sp,
                                                fontFamily = FontFamily.Default,
                                                color = MaterialTheme.colorScheme.background
                                            )
                                        }
                                    }
                                    if (!detailed) {
                                        AddButtons(action)
                                        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded) {
                                            AddNextExercise(
                                                nextExercise(
                                                    cEI = currentExerciseIndex,
                                                    cCI = currentCycleIndex,
                                                    exerciseSize = exerciseSize!!,
                                                    cycleSize = cycleSize,
                                                    viewModel = viewModel
                                                )
                                            )
                                        }
                                    } else if (windowSize.screenWidthInfo != WindowInfo.WindowType.Medium) {
                                        AddButtons(action)
                                        AddNextExercise(
                                            nextExercise(
                                                cEI = currentExerciseIndex,
                                                cCI = currentCycleIndex,
                                                exerciseSize = exerciseSize!!,
                                                cycleSize = cycleSize,
                                                viewModel = viewModel
                                            )
                                        )
                                    }
                                }

                                if (detailed) {

                                    if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded) {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.fillMaxWidth(0.5f)
                                        ) {
                                            //TODO: hacer dinámico
                                            Surface(
                                                color = Color(0x55FFFFFF),
                                                shape = RoundedCornerShape(15),
                                                modifier = Modifier.padding(start = 50.dp)
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    modifier = Modifier.padding(
                                                        start = 10.dp,
                                                        end = 10.dp
                                                    )
                                                )
                                                {
                                                    Column(
                                                        modifier = Modifier.padding(20.dp),
                                                    ) {
                                                        val cyclesList =
                                                            viewModel.uiState.cycles
                                                        cyclesList?.forEach {
                                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                                AddCycle(it)
                                                            }
                                                        }
//                                                        Row(
//                                                            modifier = Modifier.fillMaxWidth(0.25f)
//                                                        ) {
//                                                            Text(
//                                                                text = "Ciclo 1",
//                                                                fontSize = 30.sp,
//                                                                fontWeight = FontWeight.Bold,
//                                                                fontFamily = FontFamily.Default,
//                                                                color = MaterialTheme.colorScheme.background
//                                                            )
//                                                        }
//                                                        AddExercise("Ejercicio 1", "4x10", "120''")
//                                                        AddExercise("Ejercicio 2", "3x8", "180''")
//                                                        AddExercise("Ejercicio 3", "4x12", "90''")
//                                                        Row(
//                                                            modifier = Modifier.fillMaxWidth(0.25f)
//                                                        ) {
//                                                            Text(
//                                                                text = "Ciclo 2",
//                                                                fontSize = 30.sp,
//                                                                fontWeight = FontWeight.Bold,
//                                                                fontFamily = FontFamily.Default,
//                                                                color = MaterialTheme.colorScheme.background
//                                                            )
//                                                        }
//                                                        AddExercise("Ejercicio 1", "4x10", "120''")
//                                                        AddExercise("Ejercicio 2", "3x8", "180''")
//                                                        AddExercise("Ejercicio 3", "4x12", "90''")
//                                                        Row(
//                                                            modifier = Modifier.fillMaxWidth(0.25f)
//                                                        ) {
//                                                            Text(
//                                                                text = "Ciclo 3",
//                                                                fontSize = 30.sp,
//                                                                fontWeight = FontWeight.Bold,
//                                                                fontFamily = FontFamily.Default,
//                                                                color = MaterialTheme.colorScheme.background
//                                                            )
//                                                        }
//                                                        AddExercise("Ejercicio 1", "4x10", "120''")
//                                                        AddExercise("Ejercicio 2", "3x8", "180''")
//                                                        AddExercise("Ejercicio 3", "4x12", "90''")
                                                    }
                                                }
                                            }
                                        }
                                    } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.fillMaxWidth(0.7f)
                                        ) {
                                            AddButtons(action)
                                            AddNextExercise(
                                                nextExercise(
                                                    cEI = currentExerciseIndex,
                                                    cCI = currentCycleIndex,
                                                    exerciseSize = exerciseSize!!,
                                                    cycleSize = cycleSize,
                                                    viewModel = viewModel
                                                )
                                            )
                                        }
                                    }
                                }
                            } else {
                                if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.finished),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(10.dp)
                                        )
                                        FloatingActionButton(
                                            onClick = {
                                                navController.navigate("home")
                                            },
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = MaterialTheme.colorScheme.onBackground,
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(5.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Home,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(end = 5.dp)
                                                )
                                                Text(text = stringResource(R.string.to_home))
                                            }
                                        }
                                    }
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(0.75f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.finished),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(10.dp)
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        FloatingActionButton(
                                            onClick = {
                                                navController.navigate("home")
                                            },
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = MaterialTheme.colorScheme.onBackground,
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(5.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Home,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(end = 5.dp)
                                                )
                                                Text(text = stringResource(R.string.to_home))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddNextExercise(exercise: CycleExercise?) {
    if (exercise != null) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = stringResource(R.string.next),
                fontSize = 25.sp,
                fontFamily = FontFamily.Default,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            exercise.exercise?.let {
                Text(
                    text = it.name,
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Default,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = "${exercise.repetitions} reps |  ${exercise.duration}’’",
                fontSize = 25.sp,
                fontFamily = FontFamily.Default,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun AddButtons(action: (inc: Int) -> Unit) {
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
                .clip(CircleShape)
                .clickable { action(-1) }
        )
        Image(
            painter = painterResource(if (paused) R.drawable.play else R.drawable.pause),
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .clickable { paused = !paused }
        )
        Image(
            painter = painterResource(id = R.drawable.skip),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .clickable { action(1) }
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

@Composable
fun nextExercise(
    cEI: Int,
    cCI: Int,
    exerciseSize: Int,
    cycleSize: Int,
    viewModel: MainViewModel
): CycleExercise? {
    var cEIAux = cEI
    var cCIAux = cCI

    if (cEIAux + 1 == exerciseSize) {
        cCIAux += 1
        cEIAux = 0
        if (cCIAux == cycleSize) {
            return null
        }
    }
    return viewModel.uiState.cycles?.get(cCIAux)?.exercises?.get(cEIAux)
}

//@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
//@Composable
//fun PreviewExecuteRoutine1() {
//    val nav = rememberNavController()
//    VFitTheme {
//        ExecuteRoutineScreen(nav,true, 1)
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
//@Composable
//fun PreviewExecuteRoutine2() {
//    val nav = rememberNavController()
//    VFitTheme {
//        ExecuteRoutineScreen(nav,true, 1)
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
//@Composable
//fun PreviewExecuteRoutine3() {
//    val nav = rememberNavController()
//    VFitTheme {
//        ExecuteRoutineScreen(nav,true, 1)
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
//@Composable
//fun PreviewExecuteRoutine4() {
//    val nav = rememberNavController()
//    VFitTheme {
//        ExecuteRoutineScreen(nav,true, 1)
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
//@Composable
//fun PreviewExecuteRoutine5() {
//    val nav = rememberNavController()
//    VFitTheme {
//        ExecuteRoutineScreen(nav,true, 1)
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
//@Composable
//fun PreviewExecuteRoutine6() {
//    val nav = rememberNavController()
//    VFitTheme {
//        ExecuteRoutineScreen(nav,true, 1)
//    }
//}