package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.withConsumedWindowInsets
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import coil.compose.AsyncImage

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
) {

    val windowSize = rememberWindowInfo()
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize(1f),
                //.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            var search by rememberSaveable { mutableStateOf("") }

            SearchBar(search) { search = it }
            if(search.isNotEmpty()) {

                SearchContent(search, windowSize)
            }
            else {
                Spacer(modifier = Modifier.size(10.dp))
                Pagination()

            }

        }
    }
}

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
            label = { Text(stringResource(R.string.search)) },
            singleLine = true,
            shape = RoundedCornerShape(size = 10.dp),
            modifier = Modifier.height(50.dp),

            trailingIcon = {
                IconButton(onClick = { /*TODO: search*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
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
fun Pagination() {

    Column {
        val pages = listOf("Your Routines", "Liked", "All")
        var selected = remember { mutableIntStateOf(0) }
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
                    onClick = { selected.intValue = i },
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
                FilterDialog(onDismissRequest = { openDialog.value = false })
            }
        }
        PaginationContent(pages[selected.intValue])
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(onDismissRequest: () -> Unit) {
    AlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Spacer(modifier = Modifier.size(30.dp))


        }
    }
}

@Composable 
fun DropD(){


}



@Composable
fun PaginationContent(str: String) {
    ListRoutineView()
}

@Composable
fun ListRoutineView() {
    // need to make a list of routines
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        for (i in 0..6) {
            RoutineItem("Routine", "Description Lorem impsum Lorem impsum Lorem impsum Lorem impsum Lorem impsum", 30, 4)
            Divider()
        }
        Spacer(modifier = Modifier.size(60.dp))
    }
}

@Composable
fun RoutineItem(title:String,description:String, time:Int, score:Int) {
    Row(
        horizontalArrangement = Arrangement.Start, modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 1.dp)
    ) {

        ListItem(
            modifier = Modifier
                .height(100.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .padding(0.dp),
            headlineContent = {
                Text(fontWeight = FontWeight(700), text = title)
            },


            supportingContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    text = description
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

            Chip(name = "$time\"") {

                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Chip(name = "4") {

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
@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun SearchScreenPreview() {
    VFitTheme {
        SearchScreen()
    }
}


@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun SearchScreenPreview1() {
    VFitTheme {
        SearchScreen()
    }
}
@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
@Composable
fun SearchScreenPreview2() {
    VFitTheme {
        SearchScreen()
    }
}