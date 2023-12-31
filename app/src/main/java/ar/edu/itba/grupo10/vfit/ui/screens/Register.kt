package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.main.MainAppState
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.codeToMessage
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory
import ar.edu.itba.grupo10.vfit.utils.resToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    appState: MainAppState,
    onRegisterSuccess: () -> Unit
) {
    val windowSize = rememberWindowInfo()
    val uiState = viewModel.uiState

    var username by rememberSaveable { mutableStateOf("") }
    var mail by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var avatar by rememberSaveable { mutableStateOf("") }

    var expanded by rememberSaveable { mutableStateOf(false) }
    val genders = arrayOf(R.string.male, R.string.female)
    var gender by rememberSaveable { mutableIntStateOf(genders[0]) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.create_account),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp
        )

        TextField(
            value = mail,
            onValueChange = { mail = it },
            label = { Text(text = stringResource(R.string.enter_mail)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null) },
            singleLine = true
        )

        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(R.string.enter_username)) },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 4.dp),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.enter_password)) },
                    visualTransformation =
                    if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            Icon(
                                imageVector = if (passwordHidden) Icons.Default.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                )
            }

            Text(
                text = stringResource(R.string.extra_info),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(text = stringResource(R.string.enter_name)) },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 4.dp),
                    leadingIcon = { Icon(Icons.Default.TextFields, contentDescription = null) },
                    singleLine = true
                )

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(text = stringResource(R.string.enter_lastname)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    leadingIcon = { Icon(Icons.Default.TextFields, contentDescription = null) },
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
                    onExpandedChange = { expanded = !expanded }
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
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(R.string.enter_username)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = { Text(text = stringResource(R.string.enter_password)) },
                visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        Icon(
                            imageVector = if (passwordHidden) Icons.Default.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )

            Text(
                text = stringResource(R.string.extra_info),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = stringResource(R.string.enter_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                leadingIcon = { Icon(Icons.Default.TextFields, contentDescription = null) },
                singleLine = true
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = stringResource(R.string.enter_lastname)) },
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
                onExpandedChange = { expanded = !expanded }
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
                    leadingIcon = { Icon(Icons.Default.Transgender, contentDescription = null) },
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

        var checkedState by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = !checkedState },
            )
            Text(
                text = stringResource(R.string.terms_and_conditions),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 10.dp),
            )
        }

        Button(
            onClick = {
                viewModel.register(
                    username, mail, password,
                    firstName, lastName, phone, resToString(gender), avatar,
                    onRegisterSuccess
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 32.dp)
        ) {
            if (uiState.isLoading)
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surfaceTint
                )
            else
                Text(
                    text = stringResource(R.string.register),
                    color = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
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