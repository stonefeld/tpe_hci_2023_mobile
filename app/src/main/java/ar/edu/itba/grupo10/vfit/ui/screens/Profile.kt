package ar.edu.itba.grupo10.vfit.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Transgender
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import ar.edu.itba.grupo10.vfit.utils.OnLifeCycleEvent
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    onLogoutSuccess: () -> Unit = {}
) {
    val windowSize = rememberWindowInfo()
    val uiState = viewModel.uiState
    val user = uiState.currentUser

    var edit by rememberSaveable { mutableStateOf(false) }

    var firstName by rememberSaveable { mutableStateOf(user?.firstName!!) }
    var lastName by rememberSaveable { mutableStateOf(user?.lastName!!) }
    var phone by rememberSaveable { mutableStateOf(user?.phone!!) }
    var avatar by rememberSaveable { mutableStateOf(user?.avatarUrl!!) }

    // TODO: gender que tome del user
    var expanded by remember { mutableStateOf(false) }
    val genders = arrayOf("Male", "Female")
    var gender by remember { mutableStateOf(genders[0]) }

    OnLifeCycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.getCurrentUser()
            }

            else -> {}
        }
    }

    if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(600.dp)
                .padding(35.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(start = 25.dp)
                    .fillMaxWidth(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user?.avatarUrl)
                        .crossfade(true).build(),
                    placeholder = painterResource(R.drawable.guest),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = user?.username!!,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )

            }
            Box(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Column {
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




                }

                Column(
                    modifier = Modifier.padding(start = 400.dp)

                ) {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .padding(start = 25.dp)
                            .fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

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

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { if (edit) expanded = !expanded }
                        ) {
                            TextField(
                                value = gender,
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
                                        text = { Text(text = item) },
                                        onClick = {
                                            gender = item
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .padding(start = 25.dp)
                            .fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Transgender,
                            contentDescription = stringResource(id = R.string.enter_mail)
                        )
                        Text(
                            text = "Gender",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(start = 10.dp),
                        )
                    }
                }
            }

            if (!edit) {
                ElevatedButton(
                    onClick = { edit = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.edit_profile),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                ElevatedButton(
                    onClick = {
                        viewModel.logout(onLogoutSuccess)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (viewModel.uiState.isLoading)
                        CircularProgressIndicator()
                    else
                        Text(
                            text = stringResource(R.string.logout),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                }
            } else
            {
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

                ElevatedButton(
                    onClick = {
                        viewModel.modifyCurrentUser(
                            firstName,
                            lastName,
                            phone,
                            gender.lowercase(),
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
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                ElevatedButton(
                    onClick = {
                        firstName = user!!.firstName
                        lastName = user.lastName
                        phone = user.phone!!
                        gender = user.gender!!
                        avatar = user.avatarUrl!!
                        edit = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }

        }
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.getCurrentUser(true) }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.currentUser != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(user?.avatarUrl)
                                .crossfade(true).build(),
                            placeholder = painterResource(R.drawable.guest),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = user?.username!!,
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(start = 10.dp),
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
                            value = gender,
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
                                    text = { Text(text = item) },
                                    onClick = {
                                        gender = item
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    if (!edit) {
                        ElevatedButton(
                            onClick = { edit = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.edit_profile),
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }

                        ElevatedButton(
                            onClick = {
                                viewModel.logout(onLogoutSuccess)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (viewModel.uiState.isLoading)
                                CircularProgressIndicator()
                            else
                                Text(
                                    text = stringResource(R.string.logout),
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

                        ElevatedButton(
                            onClick = {
                                viewModel.modifyCurrentUser(
                                    firstName,
                                    lastName,
                                    phone,
                                    gender.lowercase(),
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
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }

                        ElevatedButton(
                            onClick = {
                                firstName = user!!.firstName
                                lastName = user.lastName
                                phone = user.phone!!
                                gender = user.gender!!
                                avatar = user.avatarUrl!!
                                edit = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


//@Preview(showSystemUi = true, locale = "es")
//@Composable
//fun ProfileScreenPreview() {
//    VFitTheme {
//        ProfileScreen(
//            //text = R.string.profile
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
//@Composable
//fun ProfileScreenPreview1() {
//    VFitTheme {
//        ProfileScreen(
//            //text = R.string.profile
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=830dp,height=490dp")
//@Composable
//fun ProfileScreenPreview2() {
//    VFitTheme {
//        ProfileScreen()
//    }
//}