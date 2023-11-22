package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.main.MainAppState
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.codeToMessage
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.resToString
import ar.edu.itba.grupo10.vfit.utils.stringToRes
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    appState: MainAppState,
    onLogoutSuccess: () -> Unit
) {
    val windowSize = rememberWindowInfo()
    val uiState = viewModel.uiState
    val user = uiState.currentUser

    var edit by rememberSaveable { mutableStateOf(false) }

    OnLifeCycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.getCurrentUser()
            }

            else -> {}
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(uiState.isLoading),
        onRefresh = { viewModel.getCurrentUser(true) },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (user != null) {
                var firstName by rememberSaveable { mutableStateOf(user.firstName) }
                var lastName by rememberSaveable { mutableStateOf(user.lastName) }
                var phone by rememberSaveable { mutableStateOf(user.phone) }
                var avatar by rememberSaveable { mutableStateOf(user.avatarUrl) }

                var expanded by rememberSaveable { mutableStateOf(false) }
                val genders = arrayOf(R.string.male, R.string.female)
                var gender by rememberSaveable { mutableIntStateOf(stringToRes(user.gender!!)) }

                if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user.avatarUrl.ifEmpty { R.drawable.guest })
                                .crossfade(true).build(),
                            placeholder = painterResource(R.drawable.guest),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = user.username,
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(start = 10.dp),
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 8.dp)
                    ) {
                        TextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            readOnly = !edit,
                            label = { Text(stringResource(R.string.enter_name)) },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 4.dp),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.TextFields,
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )

                        TextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            readOnly = !edit,
                            label = { Text(stringResource(R.string.enter_lastname)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.TextFields,
                                    contentDescription = null
                                )
                            },
                            singleLine = true
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        TextField(
                            value = phone,
                            onValueChange = { phone = it },
                            readOnly = !edit,
                            label = { Text(text = stringResource(R.string.enter_phone)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 4.dp),
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                            singleLine = true
                        )

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { if (edit) expanded = !expanded }
                        ) {
                            TextField(
                                value = stringResource(gender),
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .padding(start = 4.dp),
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Transgender,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                genders.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(stringResource(item)) },
                                        onClick = {
                                            gender = item
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user.avatarUrl?.ifEmpty { R.drawable.guest })
                                .crossfade(true).build(),
                            placeholder = painterResource(R.drawable.guest),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = user.username,
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp
                        )
                    }

                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        readOnly = !edit,
                        label = { Text(stringResource(R.string.enter_name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp, top = 16.dp),
                        leadingIcon = { Icon(Icons.Default.TextFields, contentDescription = null) },
                        singleLine = true
                    )

                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        readOnly = !edit,
                        label = { Text(stringResource(R.string.enter_lastname)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                        leadingIcon = { Icon(Icons.Default.TextFields, contentDescription = null) },
                        singleLine = true
                    )

                    TextField(
                        value = phone,
                        onValueChange = { phone = it },
                        readOnly = !edit,
                        label = { Text(text = stringResource(R.string.enter_phone)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        singleLine = true
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { if (edit) expanded = !expanded }
                    ) {
                        TextField(
                            value = stringResource(gender),
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 8.dp),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Transgender,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            genders.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(stringResource(item)) },
                                    onClick = {
                                        gender = item
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                if (!edit) {
                    Button(
                        onClick = { edit = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.edit_profile),
                            color = MaterialTheme.colorScheme.surfaceTint,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.logout(onLogoutSuccess)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        if (uiState.isLoading)
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.surfaceTint
                            )
                        else
                            Text(
                                text = stringResource(R.string.logout),
                                color = MaterialTheme.colorScheme.surfaceTint,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                    }
                } else {
                    TextField(
                        value = avatar,
                        onValueChange = { avatar = it },
                        label = { Text(text = stringResource(R.string.url_photo)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Panorama,
                                contentDescription = null
                            )
                        },
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            viewModel.modifyCurrentUser(
                                firstName,
                                lastName,
                                phone,
                                resToString(gender),
                                avatar,
                                onModifySuccess = { edit = false }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.save_changes),
                            color = MaterialTheme.colorScheme.surfaceTint,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            firstName = user.firstName
                            lastName = user.lastName
                            phone = user.phone
                            gender = stringToRes(user.gender!!)
                            avatar = user.avatarUrl
                            edit = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = MaterialTheme.colorScheme.surfaceTint,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }

            if (uiState.error != null) {
                appState.showSnackbar(
                    stringResource(codeToMessage(uiState.error)),
                    { viewModel.dismissMessage() },
                    { viewModel.dismissMessage() }
                )
            }
        }
    }
}