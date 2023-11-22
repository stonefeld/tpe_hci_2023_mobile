package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.data.models.Routine
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RoutineCard(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    data: Routine
) {
    val favorites = viewModel.uiState.favorites
    var liked = favorites?.contains(data) ?: false

    Card(
        border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(horizontal = 5.dp)
            .size(100.dp),
    ) {
        Box(
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().padding(top = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.metadata?.image?.ifEmpty { R.drawable.routine })
                            .crossfade(true).build(),
                        placeholder = painterResource(R.drawable.routine),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .fillMaxSize(0.4f)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20))
                            .clip(RoundedCornerShape(20))
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Chip(data.score.toString(), Icons.Default.Star)
                        Chip(
                            stringResource(stringToRes(data.difficulty)),
                            Icons.Default.FitnessCenter
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Chip(
                            stringResource(if (data.isPublic) R.string.public_routine else R.string.private_routine),
                            if (data.isPublic) Icons.Default.LockOpen else Icons.Default.Lock
                        )
                        data.user?.let {
                            Chip(it.username, Icons.Default.AccountCircle)
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
                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                Icon(
                    imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
    }
}