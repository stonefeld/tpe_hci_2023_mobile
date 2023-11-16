package ar.edu.itba.grupo10.vfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.components.NavigationBar
import ar.edu.itba.grupo10.vfit.data.network.models.NetworkData
import ar.edu.itba.grupo10.vfit.screens.HomeScreen
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VFitTheme {
                val appState = rememberMyAppState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { appState.snackbarHostState },
                    bottomBar = { NavigationBar() }
                ) { contentPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(contentPadding),
                        viewModel = viewModel(),
                        appState = appState
                    )
                }
            }
        }
    }

}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    appState: MainAppState
) {
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.loading), fontSize = 16.sp)
                }
            } else {
                val list = uiState.users?.data.orEmpty()

                LazyVerticalGrid(
                    state = rememberLazyGridState(),
                    columns = GridCells.Adaptive(minSize = 250.dp)
                ) {
                    items(
                        count = list.size,
                        key = { index -> list[index].id.toString() }
                    ) { index ->
                        UserCard(list[index])
                    }
                }
            }

            ElevatedButton(
                onClick = {
                    viewModel.fetchUsers(1)
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

            if (uiState.hasError) {
                appState.showSnackbar(
                    uiState.message!!,
                    { viewModel.dismissMessage() },
                    { viewModel.dismissMessage() }
                )
            }
        }
    }
}

@Composable
fun UserCard(data: NetworkData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        elevation = cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            AsyncImage(
                model = data.avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "${data.firstName} - ${data.lastName}",
                    fontSize = 16.sp
                )
                Text(
                    text = data.email,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun UserCardPreview() {
    UserCard(
        NetworkData(
            id = 1,
            email = "test@test.com",
            firstName = "nombre",
            lastName = "apellido",
            avatar = "https://reqres.in/img/faces/1-image.jpg"
        )
    )
}