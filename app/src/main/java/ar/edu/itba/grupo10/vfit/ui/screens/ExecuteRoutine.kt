package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ZoomInMap
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ExecuteRoutineScreen(
    navController: NavHostController,
    routineID: Int?,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    if (routineID != null) {
        var detailed by rememberSaveable { mutableStateOf(false) }
        var finished by rememberSaveable { mutableStateOf(false) }
        var paused by rememberSaveable { mutableStateOf(false) }
        val windowSize = rememberWindowInfo()
        var image = R.drawable.execute_routine_phone
        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
            image = R.drawable.execute_routine_phone
        } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
            image = R.drawable.execute_routine_tablet
        }
        var next by rememberSaveable { mutableStateOf(false) }

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
                        Column(
                            Modifier.verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                var currentCycleIndex by rememberSaveable { mutableIntStateOf(0) }
                                var currentExerciseIndex by rememberSaveable { mutableIntStateOf(0) }

                                val cycleSize = viewModel.uiState.cycles!!.size
                                if (cycleSize != 0) {
                                    val exerciseSize =
                                        viewModel.uiState.cycles!![currentCycleIndex].exercises?.size
                                    if (exerciseSize != 0) {
                                        if (!finished) {
                                            val timerTime by rememberSaveable {
                                                mutableLongStateOf(
                                                    (viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                        currentExerciseIndex
                                                    )?.duration ?: 10f).toLong() * 1000L
                                                )
                                            }

                                            val action: (inc: Int) -> Unit = { inc ->
                                                if (currentExerciseIndex + inc == exerciseSize) {
                                                    currentCycleIndex += 1
                                                    currentExerciseIndex = 0
                                                    if (currentCycleIndex == cycleSize) {
                                                        currentCycleIndex = 0
                                                        finished = true
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
                                                next = true
                                            }

                                            val pauseResume: (bool: Boolean?) -> Unit = { bool ->
                                                paused = bool ?: !paused
                                            }

                                            if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                                                if (detailed) {
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(5.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                AsyncImage(
                                                                    model = ImageRequest
                                                                        .Builder(LocalContext.current)
                                                                        .data(it.metadata?.image?.ifEmpty { R.drawable.exercise })
                                                                        .crossfade(true).build(),
                                                                    placeholder = painterResource(R.drawable.exercise),
                                                                    contentDescription = null,
                                                                    modifier = Modifier.size(230.dp)
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                Text(
                                                                    text = it.name,
                                                                    fontSize = 35.sp,
                                                                    fontFamily = FontFamily.Default,
                                                                    color = MaterialTheme.colorScheme.surfaceTint,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(5.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.let {
                                                                if (it.repetitions > 0 && it.duration > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps | ${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else if (it.repetitions > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else {
                                                                    Text(
                                                                        text = "${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(top = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles?.let { cycles ->
                                                                cycles[currentCycleIndex].exercises?.let {
                                                                    Timer(
                                                                        paused,
                                                                        totalTime = it[currentExerciseIndex].duration.toLong() * 1000L,
                                                                        handleColor = MaterialTheme.colorScheme.tertiary,
                                                                        inactiveBarColor = Color.White,
                                                                        activeBarColor = MaterialTheme.colorScheme.tertiary,
                                                                        modifier = Modifier.size(
                                                                            200.dp
                                                                        ),
                                                                        next = next
                                                                    )
                                                                    next = false
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(bottom = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            AddButtons(action, pauseResume, paused)
                                                        }

                                                        Row(
                                                            modifier = Modifier
                                                                .padding(5.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
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
                                                } else {
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                Text(
                                                                    text = it.name,
                                                                    fontSize = 35.sp,
                                                                    fontFamily = FontFamily.Default,
                                                                    color = MaterialTheme.colorScheme.surfaceTint,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(5.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.let {
                                                                if (it.repetitions > 0 && it.duration > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps | ${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else if (it.repetitions > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else {
                                                                    Text(
                                                                        text = "${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(top = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles?.let { cycles ->
                                                                cycles[currentCycleIndex].exercises?.let {
                                                                    Timer(
                                                                        paused,
                                                                        totalTime = it[currentExerciseIndex].duration.toLong() * 1000L,
                                                                        handleColor = MaterialTheme.colorScheme.tertiary,
                                                                        inactiveBarColor = Color.White,
                                                                        activeBarColor = MaterialTheme.colorScheme.tertiary,
                                                                        modifier = Modifier.size(
                                                                            200.dp
                                                                        ),
                                                                        next = next
                                                                    )
                                                                    next = false
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(bottom = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            AddButtons(action, pauseResume, paused)
                                                        }
                                                    }
                                                }
                                            } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
                                                if (detailed) {
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(2 / 7f),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(top = 5.dp),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                AsyncImage(
                                                                    model = ImageRequest
                                                                        .Builder(LocalContext.current)
                                                                        .data(it.metadata?.image?.ifEmpty { R.drawable.exercise })
                                                                        .crossfade(true).build(),
                                                                    placeholder = painterResource(R.drawable.exercise),
                                                                    contentDescription = null,
                                                                    modifier = Modifier.size(230.dp)
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                Text(
                                                                    text = it.name,
                                                                    fontSize = 35.sp,
                                                                    fontFamily = FontFamily.Default,
                                                                    color = MaterialTheme.colorScheme.surfaceTint,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(bottom = 5.dp),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.let {
                                                                if (it.repetitions > 0 && it.duration > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps | ${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else if (it.repetitions > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else {
                                                                    Text(
                                                                        text = "${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(3 / 5f),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(top = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles?.let { cycles ->
                                                                cycles[currentCycleIndex].exercises?.let {
                                                                    Timer(
                                                                        paused,
                                                                        totalTime = it[currentExerciseIndex].duration.toLong() * 1000L,
                                                                        handleColor = MaterialTheme.colorScheme.tertiary,
                                                                        inactiveBarColor = Color.White,
                                                                        activeBarColor = MaterialTheme.colorScheme.tertiary,
                                                                        modifier = Modifier.size(
                                                                            200.dp
                                                                        ),
                                                                        next = next
                                                                    )
                                                                    next = false
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(bottom = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            AddButtons(action, pauseResume, paused)
                                                        }
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(10.dp),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
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
                                                } else {
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(0.5f),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.End
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(top = 5.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                Text(
                                                                    text = it.name,
                                                                    fontSize = 35.sp,
                                                                    fontFamily = FontFamily.Default,
                                                                    color = MaterialTheme.colorScheme.surfaceTint,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(bottom = 5.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.let {
                                                                if (it.repetitions > 0 && it.duration > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps | ${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else if (it.repetitions > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else {
                                                                    Text(
                                                                        text = "${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(top = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles?.let { cycles ->
                                                                cycles[currentCycleIndex].exercises?.let {
                                                                    Timer(
                                                                        paused,
                                                                        totalTime = it[currentExerciseIndex].duration.toLong() * 1000L,
                                                                        handleColor = MaterialTheme.colorScheme.tertiary,
                                                                        inactiveBarColor = Color.White,
                                                                        activeBarColor = MaterialTheme.colorScheme.tertiary,
                                                                        modifier = Modifier.size(
                                                                            200.dp
                                                                        ),
                                                                        next = next
                                                                    )
                                                                    next = false
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(bottom = 10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            AddButtons(action, pauseResume, paused)
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (detailed) {
                                                    Column(
                                                        modifier = Modifier
                                                            .padding(5.dp)
                                                            .fillMaxWidth(1 / 3f),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                AsyncImage(
                                                                    model = ImageRequest
                                                                        .Builder(LocalContext.current)
                                                                        .data(it.metadata?.image?.ifEmpty { R.drawable.exercise })
                                                                        .crossfade(true).build(),
                                                                    placeholder = painterResource(R.drawable.exercise),
                                                                    contentDescription = null,
                                                                    modifier = Modifier.size(230.dp)
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                Text(
                                                                    text = it.name,
                                                                    fontSize = 35.sp,
                                                                    fontFamily = FontFamily.Default,
                                                                    color = MaterialTheme.colorScheme.surfaceTint,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.let {
                                                                if (it.repetitions > 0 && it.duration > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps | ${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else if (it.repetitions > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else {
                                                                    Text(
                                                                        text = "${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles?.let { cycles ->
                                                                cycles[currentCycleIndex].exercises?.let {
                                                                    Timer(
                                                                        paused,
                                                                        totalTime = it[currentExerciseIndex].duration.toLong() * 1000L,
                                                                        handleColor = MaterialTheme.colorScheme.tertiary,
                                                                        inactiveBarColor = Color.White,
                                                                        activeBarColor = MaterialTheme.colorScheme.tertiary,
                                                                        modifier = Modifier.size(
                                                                            200.dp
                                                                        ),
                                                                        next = next
                                                                    )
                                                                    next = false
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            AddButtons(action, pauseResume, paused)
                                                        }
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .padding(20.dp)
                                                            .fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
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
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(1 / 3f),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                AsyncImage(
                                                                    model = ImageRequest
                                                                        .Builder(LocalContext.current)
                                                                        .data(it.metadata?.image?.ifEmpty { R.drawable.exercise })
                                                                        .crossfade(true).build(),
                                                                    placeholder = painterResource(R.drawable.exercise),
                                                                    contentDescription = null,
                                                                    modifier = Modifier.size(230.dp)
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier.padding(top = 5.dp)
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.exercise?.let {
                                                                Text(
                                                                    text = it.name,
                                                                    fontSize = 35.sp,
                                                                    fontFamily = FontFamily.Default,
                                                                    color = MaterialTheme.colorScheme.surfaceTint,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier.padding(bottom = 5.dp)
                                                        ) {
                                                            viewModel.uiState.cycles!![currentCycleIndex].exercises?.get(
                                                                currentExerciseIndex
                                                            )?.let {
                                                                if (it.repetitions > 0 && it.duration > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps | ${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else if (it.repetitions > 0) {
                                                                    Text(
                                                                        text = "${it.repetitions} reps",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                } else {
                                                                    Text(
                                                                        text = "${it.duration}’’",
                                                                        fontSize = 35.sp,
                                                                        fontFamily = FontFamily.Default,
                                                                        color = MaterialTheme.colorScheme.surfaceTint,
                                                                        textAlign = TextAlign.Center
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth(0.5f),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            viewModel.uiState.cycles?.let { cycles ->
                                                                cycles[currentCycleIndex].exercises?.let {
                                                                    Timer(
                                                                        paused,
                                                                        totalTime = it[currentExerciseIndex].duration.toLong() * 1000L,
                                                                        handleColor = MaterialTheme.colorScheme.tertiary,
                                                                        inactiveBarColor = Color.White,
                                                                        activeBarColor = MaterialTheme.colorScheme.tertiary,
                                                                        modifier = Modifier.size(
                                                                            200.dp
                                                                        ),
                                                                        next = next
                                                                    )
                                                                    next = false
                                                                }
                                                            }
                                                        }
                                                        Row(
                                                            modifier = Modifier
                                                                .padding(10.dp)
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center
                                                        ) {
                                                            AddButtons(action, pauseResume, paused)
                                                        }
                                                    }
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
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
                                                        modifier = Modifier.size(300.dp)
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
                                                        modifier = Modifier.size(300.dp)
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
                                    } else {
                                        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                                            Column(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Image(
                                                        painter = painterResource(R.drawable.error_400),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(300.dp)
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.no_exercises),
                                                        color = MaterialTheme.colorScheme.background
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
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
                                        } else {
                                            Column(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(0.5f),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Image(
                                                        painter = painterResource(R.drawable.error_400),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(300.dp)
                                                    )
                                                }
                                            }
                                            Column(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.no_exercises),
                                                        color = MaterialTheme.colorScheme.background
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
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
                                } else {
                                    if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                                        Column(
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .fillMaxWidth(),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.error_400),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(300.dp)
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.no_cycles),
                                                    color = MaterialTheme.colorScheme.background
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
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
                                    } else {
                                        Column(
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .fillMaxWidth(0.5f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.error_400),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(300.dp)
                                                )
                                            }
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .fillMaxWidth(),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.no_cycles),
                                                    color = MaterialTheme.colorScheme.background
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
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
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                ) {
                                    Icon(
                                        imageVector = if (detailed) Icons.Default.ZoomInMap else Icons.Default.ZoomOutMap,
                                        contentDescription = null,
                                    )
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
    Row(modifier = Modifier.padding(20.dp)) {
        if (exercise != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.next),
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Default,
                        color = MaterialTheme.colorScheme.surfaceTint,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    exercise.exercise?.let {
                        Text(
                            text = it.name,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceTint,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (exercise.repetitions > 0 && exercise.duration > 0) {
                        Text(
                            text = "${exercise.repetitions} reps | ${exercise.duration}’’",
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceTint,
                            textAlign = TextAlign.Center
                        )
                    } else if (exercise.repetitions > 0) {
                        Text(
                            text = "${exercise.repetitions} reps",
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceTint,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "${exercise.duration}’’",
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceTint,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddButtons(action: (inc: Int) -> Unit, pauseResume: (bool: Boolean?) -> Unit, paused: Boolean) {
    Row(
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
            painter = painterResource(if (!paused) R.drawable.play else R.drawable.pause),
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .clickable { pauseResume(null) }
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
fun nextExercise(
    cEI: Int,
    cCI: Int,
    exerciseSize: Int,
    cycleSize: Int,
    viewModel: MainViewModel
): CycleExercise? {
    var cEIAux = cEI + 1
    var cCIAux = cCI

    if (cEIAux == exerciseSize) {
        cCIAux += 1
        cEIAux = 0
        if (cCIAux == cycleSize) {
            return null
        }
    }
    return viewModel.uiState.cycles?.get(cCIAux)?.exercises?.get(cEIAux)
}

@Composable
fun Timer(
    paused: Boolean,
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    next: Boolean = false,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableFloatStateOf(initialValue)
    }
    var currentTime by remember {
        mutableLongStateOf(totalTime)
    }
    if (next) {
        currentTime = totalTime
        value = 1f
    }

    LaunchedEffect(key1 = currentTime, key2 = paused) {
        if (currentTime > 0 && paused) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ) {
        Canvas(modifier = modifier) {
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (250f * value + 145f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = cos(beta) * r
            val b = sin(beta) * r
            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        Text(
            text = (currentTime / 1000L).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surfaceTint,
            textAlign = TextAlign.Center
        )
    }
}