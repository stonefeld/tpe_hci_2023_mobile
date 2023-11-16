package ar.edu.itba.grupo10.vfit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {

            Text(
                text = stringResource(R.string.welcome_back),
                fontSize = 40.sp
            )

            Image(
                modifier = Modifier
                    .width(width = 250.dp)
                    .padding(25.dp)
                    .clip(shape = RoundedCornerShape(size = 12.dp)),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                )

            var mail by rememberSaveable { mutableStateOf("") };

            TextField(
                value = mail,
                onValueChange = { mail = it },
                label = { Text("Enter Mail") },
                singleLine = true
            )

            var password by rememberSaveable { mutableStateOf("") }
            var passwordHidden by rememberSaveable { mutableStateOf(true) }

            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = { Text("Enter password") },
                visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.padding(25.dp),
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) Icons.Default.Add else Icons.Filled.Clear // cambiar iconitos
                        // Please provide localized description for accessibility services
                        val description = if (passwordHidden) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            )

            OutlinedButtonExample {

            }

        }
    }
}

@Composable
fun OutlinedButtonExample(onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }) {
        Text(text = stringResource(R.string.login),
            fontSize = 30.sp)

    }
}
@Preview(showSystemUi = true, locale = "es")
@Composable
fun LoginScreenPreview() {
    VFitTheme {
        LoginScreen()
    }
}