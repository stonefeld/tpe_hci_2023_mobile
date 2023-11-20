package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import coil.compose.AsyncImage
import java.util.Date

@Composable
fun RoutineCard(
    modifier: Modifier = Modifier,
    data: Routine
) {
    val liked by remember { mutableStateOf(false) }
    Card(
        border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.size(50.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "4.5")
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
            if (liked) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RectangleShape)
            )
            Text(text = "${data.name} ${data.detail}", fontWeight = FontWeight.W600)
        }
    }
}


@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun RoutinePreview1() {
    VFitTheme {
        val routine = Routine(
            1,
            "LOREM",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
            "advanced",
            true,
            User(
                1,
                "paki",
                "gmail",
                "Paki",
                "Quian",
                null,
                null
            ),
            null,
            Date(2023, 10, 31),
        )
            RoutineCard(Modifier, routine)
            RoutineCard(Modifier, routine)
            RoutineCard(Modifier, routine)
    }
}