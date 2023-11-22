package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.ui.components.Chip
import ar.edu.itba.grupo10.vfit.ui.main.MainAppState
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.codeToMessage
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    appState: MainAppState,
) {
    val uiState = viewModel.uiState

    var search by rememberSaveable { mutableStateOf("") }
    val args = rememberSaveable {
        mapOf(
            "orderBy" to "date",
            "direction" to "desc"
        ).toMutableMap()
    }
    var openDialog by rememberSaveable { mutableStateOf(false) }

    val filterByCriteria = mapOf(
        R.string.my_routines to {
            args["userId"] = uiState.currentUser?.id.toString()
            viewModel.getRoutines(args)
        },
        R.string.liked_routines to { viewModel.getFavorites() },
        R.string.all_routines to {
            args.remove("userId")
            viewModel.getRoutines(args)
        }
    )
    val orderByCriteria = mapOf(
        Pair(R.string.creation_date, "asc") to "date",
        Pair(R.string.creation_date, "desc") to "date",
        Pair(R.string.routine_name, "asc") to "name",
        Pair(R.string.routine_name, "desc") to "name",
        Pair(R.string.score, "asc") to "score",
        Pair(R.string.score, "desc") to "score",
        Pair(R.string.difficulty, "asc") to "difficulty",
        Pair(R.string.difficulty, "desc") to "difficulty",
        Pair(R.string.user, "asc") to "user",
        Pair(R.string.user, "desc") to "user",
    )

    var filterBy by rememberSaveable { mutableIntStateOf(R.string.my_routines) }
    var orderBy by rememberSaveable { mutableStateOf(Pair(R.string.creation_date, "desc")) }

    OnLifeCycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.getFavorites()
                viewModel.getCurrentUserFull()
            }

            Lifecycle.Event.ON_START -> {
                viewModel.getFavorites()
                viewModel.getRoutines(args)
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            value = search,
            enabled = filterBy != R.string.liked_routines,
            onValueChange = {
                search = it
                args["search"] = search
                if (search.isEmpty())
                    args.remove("search")
                if (search.length >= 3)
                    viewModel.getRoutines(args)
            },
            placeholder = { Text(stringResource(R.string.search)) },
            singleLine = true,
            trailingIcon = {
                if (search.isNotEmpty()) {
                    IconButton(onClick = {
                        search = ""
                        args.remove("search")
                        viewModel.getRoutines(args)
                    }) {
                        Icon(Icons.Filled.Cancel, contentDescription = null)
                    }
                }
            },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") }
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            filterByCriteria.forEach { (desc, action) ->
                ElevatedButton(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(35.dp),
                    onClick = {
                        filterBy = desc
                        action()
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = if (filterBy == desc) colorScheme.primary else colorScheme.surface,
                        contentColor = if (filterBy == desc) colorScheme.surfaceTint else colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = stringResource(desc),
                        fontSize = 12.sp
                    )
                }
            }
        }
        Divider(
            thickness = 5.dp,
            color = colorScheme.primary,
            modifier = Modifier.padding(top = 10.dp)
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(viewModel.uiState.isLoading),
            onRefresh = {
                viewModel.getFavorites()
                viewModel.getRoutines(args)
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = colorScheme.onBackground,
                    contentColor = colorScheme.background
                )
            }
        ) {
            Surface(color = colorScheme.surfaceVariant) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val routines =
                            if (filterBy == R.string.liked_routines)
                                uiState.favorites.orEmpty()
                            else
                                uiState.routines.orEmpty()

                        routines.let {
                            items(
                                count = it.size,
                                key = { index -> it[index].id.toString() }
                            ) { index ->
                                RoutineItem(
                                    it[index],
                                    navController
                                )
                                if (index == it.size - 1 && filterBy != R.string.liked_routines)
                                    Spacer(modifier = Modifier.size(86.dp))
                            }
                        }
                    }

                    if (filterBy != R.string.liked_routines) {
                        FloatingActionButton(
                            modifier = Modifier
                                .align(alignment = Alignment.BottomEnd)
                                .padding(16.dp),
                            onClick = { openDialog = true },
                            containerColor = colorScheme.secondary,
                            contentColor = colorScheme.surfaceTint
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterAlt,
                                contentDescription = null
                            )
                        }
                    }

                    if (openDialog) {
                        AlertDialog(onDismissRequest = { openDialog = false }) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.order_by),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.W400
                                    )
                                    Divider(
                                        thickness = 2.dp,
                                        modifier = Modifier.padding(vertical = 5.dp)
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .verticalScroll(rememberScrollState())
                                    ) {
                                        orderByCriteria.forEach { (pair, order) ->
                                            ElevatedButton(
                                                modifier = Modifier.fillMaxWidth(),
                                                onClick = {
                                                    orderBy = pair
                                                    args["orderBy"] = order
                                                    args["direction"] = pair.second
                                                    viewModel.getRoutines(args)
                                                    openDialog = false
                                                },
                                                colors = ButtonDefaults.elevatedButtonColors(
                                                    containerColor = if (orderBy == pair) colorScheme.primary else colorScheme.surface,
                                                    contentColor = if (orderBy == pair) colorScheme.surfaceTint else colorScheme.onSurface
                                                )
                                            ) {
                                                Text(
                                                    text = stringResource(pair.first),
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(end = 8.dp)
                                                )
                                                Icon(
                                                    if (pair.second == "asc") Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                                    contentDescription = null
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
        }
    }

    if (uiState.error != null) {
        appState.showSnackbar(
            stringResource(codeToMessage(uiState.error)),
            { viewModel.dismissMessage() },
            { viewModel.dismissMessage() }
        )
    }
}

@Composable
fun RoutineItem(routine: Routine, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable { navController.navigate("routine/${routine.id}") }
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            leadingContent = {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(routine.metadata?.image?.ifEmpty { R.drawable.routine })
                        .crossfade(true).build(),
                    placeholder = painterResource(R.drawable.routine),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .size(70.dp)
                        .border(1.dp, colorScheme.primary, RoundedCornerShape(20))
                        .clip(RoundedCornerShape(20))
                )
            },
            headlineContent = { Text(routine.name, fontWeight = FontWeight.W600) },
            supportingContent = { Text(routine.detail) },
            trailingContent = {
                Column {
                    Row {
                        Chip(routine.score.toString(), Icons.Default.Star)
                        Chip(
                            stringResource(stringToRes(routine.difficulty)),
                            Icons.Default.FitnessCenter
                        )
                    }
                    Row {
                        Chip(
                            if(routine.isPublic)
                                stringResource(R.string.public_routine)
                            else
                                stringResource(R.string.private_routine),
                            if(routine.isPublic)
                                Icons.Default.LockOpen
                            else
                                Icons.Default.Lock)
                        Chip(routine.user.username, Icons.Default.Person)
                    }
                }
            }
        )
    }
}