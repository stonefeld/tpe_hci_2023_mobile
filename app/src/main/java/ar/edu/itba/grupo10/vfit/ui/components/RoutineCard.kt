package ar.edu.itba.grupo10.vfit.ui.components

import android.graphics.drawable.shapes.Shape
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.data.models.User
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Date

@Composable
fun RoutineCard(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    data: Routine
) {
    val favorites = viewModel.uiState.favorites
    var liked by remember { mutableStateOf(favorites?.contains(data) ?: false) }

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
                .padding(8.dp)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxHeight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(if (data.metadata != null) data.metadata!!.image else R.drawable.execute_routine_tablet)
                            .crossfade(true).build(),
                        placeholder = painterResource(R.drawable.execute_routine_tablet),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize(0.45f)
                            .padding(bottom = 5.dp)
                    )
                    Text(
                        text = data.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
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
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier
                                            .padding(end = 3.dp)
                                            .size(12.dp)
                                    )
                                    Text(
                                        text = "30'",
                                        fontSize = 12.sp,
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
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(12.dp)
                                    )
                                    Text(
                                        text = stringResource(stringToRes(data.difficulty)),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
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
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(12.dp)
                                    )
                                    Text(
                                        text = data.score.toString(),
                                        fontSize = 12.sp,
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
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .size(12.dp)
                                    )
                                    Text(
                                        text = data.user.username,
                                        fontSize = 12.sp,
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
            FloatingActionButton(
                modifier = Modifier.align(alignment = Alignment.TopEnd),
                onClick = {
                    liked = !liked
                    if (liked)
                        viewModel.addFavorite(data.id!!)
                    else
                        viewModel.removeFavorite(data.id!!)
                },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
}

//@Preview(locale = "es")
//@Composable
//fun RoutinePreview() {
//    VFitTheme {
//        val routine = Routine(
//            1,
//            "LOREM",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi non semper ante, in finibus erat.",
//            "advanced",
//            true,
//            User(
//                1,
//                "paki",
//                "gmail",
//                "Paki",
//                "Quian",
//                null,
//                "",
//                "",
//                true
//            ),
//            Date(2023, 10, 31),
//            null,
//            null
//        )
//        RoutineCard(Modifier, routine)
//        RoutineCard(Modifier, routine)
//        RoutineCard(Modifier, routine)
//    }
//}