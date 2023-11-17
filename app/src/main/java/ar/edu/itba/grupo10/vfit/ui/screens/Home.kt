package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.components.RoutineCard
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
) {
    val uiState = viewModel.uiState

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(2.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W600
                )
                Text(
                    text = stringResource(R.string.home_subtitle),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.loading), fontSize = 16.sp)
                }
            } else {
                val list = uiState.routines.orEmpty()

                LazyHorizontalGrid(
                    state = rememberLazyGridState(),
                    rows = GridCells.Fixed(1),
                    modifier = Modifier.heightIn(max = 140.dp)
                ) {
                    items(
                        count = list.size,
                        key = { index -> list[index].id.toString() }
                    ) { index ->
                        RoutineCard(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            data = list[index]
                        )
                    }
                }
            }

            ElevatedButton(
                onClick = {
                    viewModel.getRoutines()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.load_users),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}