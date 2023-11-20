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
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Cycle
import ar.edu.itba.grupo10.vfit.data.models.CycleExercise
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.squareup.wire.internal.copyOf
import java.util.Arrays.copyOf
import java.util.Date

@Composable
fun RoutineScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    routineID: Int?
) {
    if (routineID != null) {
        var liked by remember { mutableStateOf(false) }
        val windowSize = rememberWindowInfo()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)

        OnLifeCycleEvent { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.getRoutine(routineID)
                    viewModel.getCycles(routineID)
                }

                else -> {}
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.uiState.isLoading),
            onRefresh = {
                viewModel.getRoutine(routineID)
                viewModel.getCycles(routineID)
            }
        ) {
            //TODO: no entra siempre en el if por lo qual hay que chequear el estado de currentRoutine en cada llamado
            if (viewModel.uiState.currentRoutine != null) {
                val currentRoutine = viewModel.uiState.currentRoutine
                val cyclesList = viewModel.uiState.cycles

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(1f)
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
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(if (currentRoutine?.metadata != null) currentRoutine.metadata!!.image else R.drawable.execute_routine_tablet)
                                        .crossfade(true).build(),
                                    placeholder = painterResource(R.drawable.execute_routine_tablet),
                                    contentDescription = null,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
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
                                            modifier = Modifier.padding(vertical = 5.dp)
                                        )
                                        Text(
                                            text = currentRoutine.date.toString(),
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
                                                    painter = painterResource(R.drawable.play),
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .padding(end = 10.dp)
                                                        .clip(CircleShape)
                                                        .clickable {
                                                            /*TODO:*/
                                                        }
                                                )
                                                IconButton(onClick = { /*startActivity(shareIntent)*/ }) {
                                                    Icon(
                                                        Icons.Rounded.Share,
                                                        contentDescription = stringResource(R.string.enter_mail),
                                                        modifier = Modifier
                                                            .padding(vertical = 5.dp)
                                                    )
                                                }


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
                                        text = currentRoutine!!.detail,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
                                    cyclesList?.forEach {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            AddCycle(it)
                                        }
                                    }
                                } else {
                                    cyclesList?.forEach {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            //TODO: arreglar dos ciclos por row
                                            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                                                AddCycle(it)
                                            }
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                AddCycle(it)
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
fun AddCycle(
    cycle: Cycle,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
) {
    OnLifeCycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                cycle.id?.let { viewModel.getCycleExercises(it) }
            }

            else -> {}
        }
    }

    if (viewModel.uiState.cycleExercises != null) {
        val exercisesList = viewModel.uiState.cycleExercises

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
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            exercisesList?.forEach {
                AddExerciseRoutine(it)
            }
        }
    }
}

@Composable
fun AddExerciseRoutine(
    cycleExercise: CycleExercise
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

//@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
//@Composable
//fun RoutinePreview1() {
//    VFitTheme {
//        RoutineScreen(
//            Routine(
//                1,
//                "LOREM",
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
//                "advanced",
//                true,
//                User(
//                    1,
//                    "paki",
//                    "gmail",
//                    "Paki",
//                    "Quian",
//                    null,
//                    null,
//                    null,
//                    null,
//                    true
//                ),
//                Date(2023, 10, 31),
//                null,
//                null
//            ),
//        )
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
//@Composable
//fun RoutinePreview2() {
//    VFitTheme {
//        RoutineScreen(
//            Routine(
//                1,
//                "LOREM",
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
//                "advanced",
//                true,
//                User(
//                    1,
//                    "paki",
//                    "gmail",
//                    "Paki",
//                    "Quian",
//                    null,
//                    null,
//                    null,
//                    null,
//                    true
//                ),
//                Date(2023, 10, 31),
//                null,
//                null
//            )
//        )
//    }
//}
//
//
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
//@Composable
//fun RoutinePreview3() {
//    VFitTheme {
//        RoutineScreen(
//            Routine(
//                1,
//                "LOREM",
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
//                "advanced",
//                true,
//                User(
//                    1,
//                    "paki",
//                    "gmail",
//                    "Paki",
//                    "Quian",
//                    null,
//                    null,
//                    null,
//                    null,
//                    true
//                ),
//                Date(2023, 10, 31),
//                null,
//                null
//            )
//        )
//    }
//}