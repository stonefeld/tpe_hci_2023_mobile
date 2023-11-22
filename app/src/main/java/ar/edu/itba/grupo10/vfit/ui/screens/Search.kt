package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextButton
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import coil.compose.AsyncImage

@Composable
fun SearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {

    val uiState = viewModel.uiState
    val windowSize = rememberWindowInfo()

    OnLifeCycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.getRoutines()
                viewModel.getCurrentUser()
            }

            else -> {}
        }
    }
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize(1f),
                //.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            var search by rememberSaveable { mutableStateOf("") }

            SearchBar(search) { search = it }
            if(search.isNotEmpty() ) {

                SearchContent(search, windowSize, uiState.routines ?: emptyList(), navController)


            } else {
                Spacer(modifier = Modifier.size(10.dp))


                Pagination(uiState.routines ?: emptyList(), uiState.currentUser?.username, navController
                )
            }

            }

        }
}
@Composable
fun SearchContent(search:String, windowSize: WindowInfo, routines: List<Routine>, navController: NavHostController){

    val filteredRoutines = routines.filter { it.name.contains(search, ignoreCase = true) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    )   {
            Text(
                text = stringResource(R.string.look_for_results),
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 40.dp)
            )


            if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded) {

                if (filteredRoutines.isEmpty()) {
                    Image(
                        modifier = Modifier
                            .width(700.dp)
                            .padding(top = 25.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp)),
                        painter = painterResource(id = R.drawable.background_search),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Column {
                        for (routine in filteredRoutines) {
                            RoutineItem(
                                routine,
                                navController
                            )
                            Divider()
                        }
                    }
                }
            }
            else{

                if (filteredRoutines.isEmpty()) {
                    Image(
                        modifier = Modifier
                            .width(400.dp)
                            .padding(top = 25.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp)),
                        painter = painterResource(id = R.drawable.background_search),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                } else {

                    Column {
                        for (routine in filteredRoutines) {
                            RoutineItem(
                                routine,
                                navController
                            )
                            Divider()
                        }
                    }
                }
            }

    }

}
/*
@Composable
fun SearchContent(search:String, windowSize: WindowInfo){

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        Text(
            text = stringResource(R.string.look_for_results),
            fontSize = 40.sp,
            modifier = Modifier.padding(top = 40.dp)
        )
        if(windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded){
            Image(
                modifier = Modifier
                    .width(700.dp)
                    .padding(top = 25.dp)
                    .clip(shape = RoundedCornerShape(size = 12.dp)),
                painter = painterResource(id = R.drawable.background_search),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )}
        else{
            Image(
                modifier = Modifier
                    .width(400.dp)
                    .padding(top = 25.dp)
                    .clip(shape = RoundedCornerShape(size = 12.dp)),
                painter = painterResource(id = R.drawable.background_search),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}
*/

@Composable
fun SearchBar(search:String, onSearchChange: (String) -> Unit){
    val windowSize = rememberWindowInfo()
    val expanded = windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded
    Row(
        horizontalArrangement = expanded.let { if (it) Arrangement.Center else Arrangement.End },
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 20.dp, bottom = 10.dp)
            .fillMaxWidth()

    ){

        TextField(
            value = search,
            onValueChange = { onSearchChange(it) },
            placeholder = { Text(stringResource(R.string.search)) },
            singleLine = true,
            shape = RoundedCornerShape(size = 10.dp),
            modifier = Modifier.height(50.dp),

            trailingIcon = {
                IconButton(onClick = { /*TODO: search*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        )
            TextButton(

                contentPadding = PaddingValues(2.dp),
                onClick = { onSearchChange("") },
                shape = RoundedCornerShape(size = 10.dp),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(
                    stringResource(R.string.cancel),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }



    }

}

@Composable
fun Pagination(list: List<Routine>, username:String?, navController: NavHostController) {

    Column {
        val pages = listOf("Your Routines", "Liked", "All")
        var selected = remember { mutableIntStateOf(0) }
        var selectedSort = remember { mutableIntStateOf(-1) }

        val openDialog = remember { mutableStateOf(false) }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            for (i in pages.indices) {
                val isSelected = selected.intValue == i
                val backgroundColor =
                    if (isSelected) colorScheme.primary else MaterialTheme.colorScheme.surface
                val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                ElevatedButton(
                    onClick = { selected.intValue = i

                              },
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = backgroundColor,
                        contentColor = textColor
                    ),
                    modifier = Modifier
                        .width(95.dp)
                        .height(35.dp)
                        .padding(4.dp)
                ) {
                    Text(
                        text = pages[i],
                        fontSize = 13.sp
                    )
                }
            }
            ExtendedFloatingActionButton(
                onClick = { openDialog.value = true },
                modifier= Modifier
                    .width(110.dp)
                    .height(35.dp)
                    .padding(4.dp),
                contentColor = colorScheme.onPrimary, containerColor = colorScheme.secondary,
                icon = { Icon(Icons.Default.FilterAlt, "Filter", modifier=Modifier.size(16.dp)) },
                text = { Text(text = "Sort", fontSize = 13.sp) },
            )


        }
        Spacer(modifier = Modifier.size(10.dp))


        when    {

            openDialog.value ->{
                SortDialog(onDismissRequest = { openDialog.value = false }, selectedSort)
            }
        }

       var sublist = filterList(list,selected.intValue)
        val difficultyMap = mapOf("rookie" to 1, "beginner" to 2,"intermediate" to 3, "advanced" to 4, "expert" to 5)

        sublist = when (selectedSort.intValue) {
            0 -> sublist.sortedByDescending { it.date }
            1 -> sublist.sortedByDescending { it.score }
            2 -> sublist.sortedByDescending { difficultyMap[it.difficulty] ?: 0 }
            else -> filterList(list,selected.intValue)
        }

        ListRoutineView(sublist, navController )
    }
}

fun filterList(list:List<Routine>,selected:Int): List<Routine> {
    val sublist = when (selected) {
        1 -> list
        0 -> list.filter { it.user.username == "username" }
        2 -> list
        else -> list
    }
    return sublist
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(onDismissRequest: () -> Unit, selectedSort: MutableIntState){
    AlertDialog(onDismissRequest = { onDismissRequest()
        }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Sort by",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val sortOptions = listOf(stringResource(R.string.creation_date),stringResource(R.string.score),stringResource(R.string.difficulty),stringResource(R.string.category))
                for (i in sortOptions.indices) {
                    val isSelected = selectedSort.intValue == i
                    val backgroundColor =
                        if (isSelected) colorScheme.secondary else MaterialTheme.colorScheme.surface
                    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    ElevatedButton(
                        onClick = {
                            selectedSort.intValue = if(isSelected)
                                -1
                            else
                                i
                        },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = backgroundColor,
                            contentColor = textColor
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .padding(4.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = sortOptions[i],
                            fontSize = 13.sp
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun ListRoutineView(list:List<Routine>, navController: NavHostController) {
    // need to make a list of routines
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .verticalScroll(state = rememberScrollState())
    ) {

        for (routine in list) {
            RoutineItem(
               routine,
                navController
            )
            Divider()
        }
        Spacer(modifier = Modifier.size(60.dp))

    }
}

@Composable
fun RoutineItem(routine:Routine, navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.Start, modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 1.dp)
            .clickable { navController.navigate("routine/${routine.id}") }
    ) {

        ListItem(
            modifier = Modifier
                .height(100.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .padding(0.dp),
            headlineContent = {
                Text(fontWeight = FontWeight(700), text = routine.name)
            },


            supportingContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    text = routine.detail
                )
            },

            leadingContent = {

                AsyncImage(
                    model = "", contentDescription = "FOTO_DE_RUTINA", modifier = Modifier
                        .height(70.dp)
                        .width(65.dp)
                        .border(1.dp, colorScheme.primary, RoundedCornerShape(20))
                        .clip(CircleShape), contentScale = ContentScale.Crop
                )

            }
        )

        Row(modifier = Modifier.padding(vertical = 8.dp)) {

            Chip(name = "$30\'") {

                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Chip(name = routine.score.toString()) {

                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
fun Chip(name: String, icon: @Composable () -> Unit) {
    Surface(
        shape = RoundedCornerShape(30),
        color = colorScheme.secondary,
        modifier = Modifier
            .height(22.dp)
            .padding(horizontal = 2.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 2.dp)) {
            Text(text = name, fontSize = 14.sp)
            icon.invoke()

        }
    }
}
/*
@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun SearchScreenPreview() {
    VFitTheme {
        SearchScreen(null,null,null)
    }
}


@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun SearchScreenPreview1() {
    VFitTheme {
        SearchScreen(null,null,null)
    }
}
@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
@Composable
fun SearchScreenPreview2() {
    VFitTheme {
        SearchScreen(null,null,null)
    }
}


 */