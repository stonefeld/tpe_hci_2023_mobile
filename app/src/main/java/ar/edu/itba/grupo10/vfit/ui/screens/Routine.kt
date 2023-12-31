package ar.edu.itba.grupo10.vfit.ui.screens

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Cycle
import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RoutineScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineId: Int?
) {
    if (routineId != null) {
        val windowSize = rememberWindowInfo()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "http://www.vfit.com/routine/$routineId")
            type = "text/plain"
        }
        val context = LocalContext.current
        val shareIntent = Intent.createChooser(sendIntent, stringResource(R.string.share_routine))

        OnLifeCycleEvent { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.getFavorites()
                    viewModel.getRoutine(routineId)
                    viewModel.getCyclesFull(routineId)
                }

                else -> {}
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.uiState.isLoading),
            onRefresh = {
                viewModel.getFavorites()
                viewModel.getRoutine(routineId)
                viewModel.getCyclesFull(routineId)
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )
            }
        ) {
            Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (viewModel.uiState.currentRoutine != null) {
                        val currentRoutine = viewModel.uiState.currentRoutine
                        val cyclesList = viewModel.uiState.cycles
                        val favorites = viewModel.uiState.favorites
                        var liked = favorites?.contains(currentRoutine) ?: false
                        var showReview by remember { mutableStateOf(false) }
                        var review by remember { mutableIntStateOf(0) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .heightIn(0.dp, 150.dp),
                                color = MaterialTheme.colorScheme.surface,
                            ) {
                                currentRoutine?.let {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(it.metadata?.image?.ifEmpty { R.drawable.routine })
                                            .crossfade(true).build(),
                                        placeholder = painterResource(R.drawable.routine),
                                        contentDescription = null,
                                        modifier = Modifier.padding(bottom = 5.dp)
                                    )
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
                                    Column(modifier = Modifier.align(alignment = Alignment.TopEnd)) {
                                        FloatingActionButton(
                                            onClick = {
                                                liked = !liked
                                                if (liked)
                                                    viewModel.addFavorite(currentRoutine?.id!!)
                                                else
                                                    viewModel.removeFavorite(currentRoutine?.id!!)
                                            },
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = MaterialTheme.colorScheme.onBackground
                                        ) {
                                            Icon(
                                                imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = null,
                                            )
                                        }
                                        FloatingActionButton(
                                            modifier = Modifier.padding(top = 8.dp),
                                            onClick = { showReview = !showReview },
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = MaterialTheme.colorScheme.onBackground
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Divider(
                            thickness = 5.dp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        if (showReview) {
                            Column(
                                modifier = Modifier.padding(
                                    horizontal = 32.dp,
                                    vertical = 12.dp
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Slider(
                                    value = review.toFloat(),
                                    onValueChange = { review = it.toInt() },
                                    colors = SliderDefaults.colors(inactiveTrackColor = MaterialTheme.colorScheme.background),
                                    steps = 10,
                                    valueRange = 0f..10f
                                )
                                Button(onClick = {
                                    viewModel.reviewRoutine(currentRoutine?.id!!, review) {
                                        showReview = false
                                        review = 0
                                    }
                                }) {
                                    Text(
                                        stringResource(R.string.review) + review,
                                        color = MaterialTheme.colorScheme.surfaceTint
                                    )
                                }
                            }
                        }

                        Row {
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxHeight()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 15.dp, end = 10.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(0.65f)
                                    ) {
                                        Text(
                                            text = currentRoutine!!.name,
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(bottom = 5.dp)
                                        )
                                        Text(
                                            text = stringResource(R.string.difficulty) + " " + stringResource(
                                                stringToRes(currentRoutine.difficulty)
                                            ),
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.fillMaxSize(1f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.play),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .padding(horizontal = 5.dp)
                                                    .clip(CircleShape)
                                                    .clickable {
                                                        navController.navigate("routine/${viewModel.uiState.currentRoutine!!.id}/execute")
                                                    }
                                            )
                                            IconButton(onClick = {
                                                context.startActivity(shareIntent)
                                            }) {
                                                Icon(
                                                    Icons.Rounded.Share,
                                                    contentDescription = stringResource(R.string.enter_mail),
                                                    modifier = Modifier.padding(horizontal = 5.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                                Row(
                                    modifier = Modifier
                                        .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                                        .fillMaxWidth(1f)
                                ) {
                                    if (currentRoutine != null) {
                                        Text(
                                            text = currentRoutine.date.toString(),
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                        .fillMaxWidth(1f)
                                ) {
                                    if (currentRoutine != null) {
                                        Text(
                                            text = currentRoutine.detail,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                                if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                                    cyclesList?.forEach {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            AddCycle(it)
                                        }
                                    }
                                } else {
                                    if (cyclesList != null) {
                                        for (idx in cyclesList.indices step 2) {
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                                                    AddCycle(cyclesList[idx])
                                                }
                                                if (idx + 1 < cyclesList.size) {
                                                    Column(modifier = Modifier.fillMaxWidth()) {
                                                        AddCycle(cyclesList[idx + 1])
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
    }
}

@Composable
fun AddCycle(cycle: Cycle) {
    if (cycle.exercises != null) {
        val exercisesList = cycle.exercises

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
                    text = cycle.name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            exercisesList?.forEach {
                AddExerciseRoutine(it)
            }
        }
    }
}

@Composable
fun AddExerciseRoutine(cycleExercise: CycleExercise) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
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
                cycleExercise.exercise?.let {
                    Text(
                        text = it.name,
                        modifier = Modifier.align(Alignment.CenterStart),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    if (cycleExercise.repetitions > 0) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    end = 4.dp
                                )
                        ) {
                            Text(
                                text = cycleExercise.repetitions.toString() + " reps",
                                color = MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    if (cycleExercise.duration > 0 && cycleExercise.repetitions > 0) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 1.dp,
                                    end = 1.dp
                                )
                        ) {
                            Text(
                                text = "/",
                                color = MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    if (cycleExercise.duration > 0) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    end = 4.dp
                                )
                        ) {
                            Text(
                                text = cycleExercise.duration.toString() + "''",
                                color = MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}