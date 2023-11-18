package ar.edu.itba.grupo10.vfit.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults.elevatedButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import coil.compose.AsyncImage

@Composable
fun LibraryScreen() {
    Surface(modifier = Modifier.fillMaxSize(1f)) {
        Column(horizontalAlignment = CenterHorizontally) {
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
fun ProfileHeader() {
    // need to make a profile photo and background image
    Box {
        AsyncImage(
            model = "", contentDescription = "FOTO_PORTADA", modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.22f)
                .border(1.dp, colorScheme.primary, RectangleShape)
        )
        Column() {
            Spacer(modifier = Modifier.size(65.dp))
            Row {
                Spacer(modifier = Modifier.size(5.dp))
                AsyncImage(
                    model = "", contentDescription = "FOTO_PERFIL", modifier = Modifier
                        .size(100.dp)
                        .border(1.dp, colorScheme.primary, CircleShape)
                )
                Spacer(modifier = Modifier.size(40.dp))
                Column {
                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "User Name",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp), color = colorScheme.primary
    )
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
fun PaginationContent(str: String) {
    ListRoutineView()
}

@Composable
fun ListRoutineView() {
    // need to make a list of routines
    val name = "rutina"
    val titulo = "Epic Routine"
    val score = 4
    val tags = listOf<String>("Abs")
    val time = 30
    val description =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    Column(
        horizontalAlignment = CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 0..6) {
            RoutineItem()
            Divider()
        }
        Spacer(modifier = Modifier.size(60.dp))
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RoutineItem() {
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
                Text(fontWeight = FontWeight(700), text = "Routine")
            },


            supportingContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
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

            Chip(name = "30'") {

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