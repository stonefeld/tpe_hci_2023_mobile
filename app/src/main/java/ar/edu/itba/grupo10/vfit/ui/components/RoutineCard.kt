package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import java.util.Date

@Composable
fun RoutineCard(
    modifier: Modifier = Modifier,
    data: Routine
) {
    var liked by remember { mutableStateOf(false) }

    Card(
        border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .aspectRatio(1f)
            .size(100.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
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
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.execute_routine_tablet),
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight(0.7f),
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = data.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 5.dp),
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shadowElevation = 10.dp,
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 12.dp))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 3.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.background,
                                    modifier = Modifier.padding(end = 3.dp)
                                )
                                Text(
                                    text = "30'",
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.padding(horizontal = 5.dp),
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shadowElevation = 10.dp,
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 12.dp))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FitnessCenter,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.background,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Text(
                                    text = stringResource(stringToRes(data.difficulty)),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 5.dp),
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shadowElevation = 10.dp,
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 12.dp))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.background,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Text(
                                    text = data.score.toString(),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.padding(horizontal = 5.dp),
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shadowElevation = 10.dp,
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 12.dp))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.background,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                Text(
                                    text = data.user.username,
                                    fontWeight = FontWeight.Bold,
                                )
                                if (data.user.verified) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier.size(10.dp)
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


@Preview(locale = "es")
@Composable
fun RoutinePreview() {
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
                null,
                null,
                null,
                true
            ),
            Date(2023, 10, 31),
            null,
            null
        )
        RoutineCard(Modifier, routine)
        RoutineCard(Modifier, routine)
        RoutineCard(Modifier, routine)
    }
}