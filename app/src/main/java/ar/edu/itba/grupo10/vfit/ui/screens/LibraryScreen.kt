package ar.edu.itba.grupo10.vfit.ui.screens

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults.elevatedButtonColors
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import coil.compose.AsyncImage

@Composable
fun LibraryScreen()
 {

    Surface(modifier = Modifier.fillMaxSize(1f)) {
        Column (horizontalAlignment = CenterHorizontally){

            ProfileHeader()
            LibraryPagination()
        }
    }

}
@Preview(showSystemUi = true, locale = "es")
@Composable
fun LibraryScreenPreview() {
    VFitTheme {
        LibraryScreen()
    }
}

@Composable
fun ProfileHeader(){
   // need to make a profile photo and background image
    Column(modifier = Modifier.run {
        padding(2.dp)
            .fillMaxWidth()
            .height(200.dp)
    }) {
        Spacer(modifier = Modifier.size(10.dp))
        AsyncImage(model = "",  contentDescription = "FOTO_DE_PERFIL", modifier = Modifier
            .size(100.dp)
            .clip(CircleShape), contentScale = ContentScale.Crop)
        Divider(thickness=4.dp, color = colorScheme.primary)
    }

}
@Composable
fun LibraryPagination() {

    Column {
        val pages = listOf<String>("Your Routines", "Liked", "Follows", "History")
        var selected = remember { mutableIntStateOf(0) }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            for (i in pages.indices) {
                val isSelected = selected.intValue == i
                val backgroundColor =
                    if (isSelected) colorScheme.primary else colorScheme.surface
                val textColor = if (isSelected) colorScheme.onPrimary else colorScheme.onSurface
                ElevatedButton(
                    onClick = { selected.intValue = i },
                    contentPadding = PaddingValues(0.dp),
                    colors = elevatedButtonColors(
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
        }
        PaginationContent(pages[selected.intValue])
    }
}

@Composable
fun PaginationContent(str: String){
    //Text(text = str, fontSize = 30.sp, modifier = Modifier.padding(8.dp))
    ListRoutineView()
}


@Composable
fun ListRoutineView(){
    // need to make a list of routines
    val name = "rutina"
    val titulo = "Epic Routine"
    val score = 4
    val tags = listOf<String>("Abs")
    val time = 30
    val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    Column(horizontalAlignment = CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 6.dp)) {


       RoutineItem()
        // make a 5 routine view
        //for (i in 0..4) {
            /*
            Divider(Modifier.padding(horizontal = 8.dp))
            Row(
                modifier = Modifier
                    .padding(5.dp)
            ) {

                Text(
                    text = name,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                //AsyncImage(model = "", contentDescription = "FOTO_DE_RUTINA", modifier = Modifier.size(100.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                Column {

                    Row {
                        Text(text = titulo, fontSize = 15.sp)
                        Spacer(modifier = Modifier.size(80.dp))


                        //tag
                        Surface(
                            color = MaterialTheme.colorScheme.onSecondary,
                            shape = CircleShape
                        ) {
                            Row(modifier = Modifier.padding(2.dp)) {
                                Icon(//clock icon
                                    imageVector = Icons.Outlined.DateRange,
                                    contentDescription = "star",
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(text = "$time'", fontSize = 15.sp)
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.onSecondary,
                            shape = CircleShape
                        ) {
                            Row(modifier = Modifier.padding(2.dp)) {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = "star",
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(text = score.toString(), fontSize = 15.sp)

                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))

                        for (i in tags.indices) {

                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape,
                                modifier = Modifier
                                    .width(38.dp)
                                    .height(22.dp),

                            ) {
                                Text(text = tags[i], fontSize = 15.sp, textAlign = TextAlign.Center)

                            }
                        }
                    }
                    Text(text = description, fontSize = 15.sp, modifier = Modifier.padding(0.dp))

                }
            }
        }
    */
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RoutineItem() {


    Column {

        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .fillMaxHeight()
                .padding(0.dp),
            headlineText = {
                Text(fontWeight= FontWeight(700) , text="Routine")
            },
            supportingText = { Text( text = "Secondary text Secondary textSecondary textSecondary textSecondary textSecondary text") },
            trailingContent = {
                Column (verticalArrangement = Arrangement.Top){
                    Chip(name = "30'", icon = {
                        Icon(
                            Icons.Outlined.DateRange,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(18.dp)
                        )
                    })

                }

            },
            leadingContent = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                )
            }
        )
        Divider(Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun Chip(name : String, icon : @Composable () -> Unit ) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .height(22.dp),
    ) {
        Row(modifier = Modifier.padding(horizontal = 1.dp)) {
            icon.invoke()
            Text(text = name, fontSize = 15.sp)

        }
    }
}
