package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun VerifyAccountScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    appState: MainAppState,
    onVerifySuccess: () -> Unit
) {
    val windowSize = rememberWindowInfo()
    val uiState = viewModel.uiState

    var email by rememberSaveable { mutableStateOf("") }
    var code by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.verify),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 38.sp
        )

        Text(
            text = stringResource(id = R.string.code_email),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp)
        )

        if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded || windowSize.screenWidthInfo == WindowInfo.WindowType.Medium) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.enter_mail)) },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 4.dp),
                    leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null) },
                    singleLine = true
                )

                TextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text(stringResource(R.string.enter_code)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    singleLine = true
                )
            }
        } else {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.enter_mail)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null) },
                singleLine = true
            )

            TextField(
                value = code,
                onValueChange = { code = it },
                label = { Text(stringResource(R.string.enter_code)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                singleLine = true
            )
        }

        Button(
            onClick = {
                viewModel.verifyAccount(email, code, onVerifySuccess)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            if (uiState.isLoading)
                CircularProgressIndicator()
            else
                Text(
                    text = stringResource(R.string.send_code),
                    color = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
        }
        Button(
            onClick = { viewModel.resendVerificationCode(email) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 32.dp)
        ) {
            if (uiState.isLoading)
                CircularProgressIndicator()
            else
                Text(
                    text = stringResource(R.string.resend_code),
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