package ar.edu.itba.grupo10.vfit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {

            Text(
                text = stringResource(R.string.welcome),
                fontSize = 40.sp
            )

            var username by rememberSaveable { mutableStateOf("") };

            TextField(
                value = username,
                modifier = Modifier.padding(25.dp),
                onValueChange = { username = it },
                label = { Text( text = stringResource(R.string.enter_username)) },
                singleLine = true
            )

            var mail by rememberSaveable { mutableStateOf("") };

            TextField(
                value = mail,
                onValueChange = { mail = it },
                label = { Text( text = stringResource(R.string.enter_mail)) },
                singleLine = true
            )

            var password by rememberSaveable { mutableStateOf("") }
            var passwordHidden by rememberSaveable { mutableStateOf(true) }

            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = { Text( text = stringResource(R.string.enter_password)) },
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


            HorizontalDivider()


            Text(
                text = stringResource(R.string.extra_info),
                fontSize = 25.sp
            )
            var name by rememberSaveable { mutableStateOf("") };

            TextField(
                value = name,
                modifier = Modifier.padding(25.dp),
                onValueChange = { name = it },
                label = { Text( text = stringResource(R.string.enter_name)) },
                singleLine = true
            )
            var lastname by rememberSaveable { mutableStateOf("") };

            TextField(
                value = lastname,
                onValueChange = { lastname = it },
                label = { Text( text = stringResource(R.string.enter_lastname)) },
                singleLine = true
            )

            var phone by rememberSaveable { mutableStateOf("") };

            TextField(
                value = phone,
                modifier = Modifier.padding(25.dp),
                onValueChange = { phone = it },
                label = { Text( text = stringResource(R.string.enter_phone)) },
                singleLine = true
            )

            val context = LocalContext.current
            val coffeeDrinks = arrayOf("Male","Female")
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    coffeeDrinks.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }



            var checkedState by rememberSaveable { mutableStateOf(false) };


            Row{
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = stringResource(R.string.terms),
                    fontSize = 12.sp
                )
            }



            OutlinedButtonRegister {

            }



        }
    }
}

@Composable
fun OutlinedButtonRegister(onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }) {
        Text(text = stringResource(R.string.register),
            fontSize = 20.sp)

    }
}

@Composable
fun OutlinedButtonGender(onClick: () -> Unit, function: () -> Unit) {
    OutlinedButtonGender(onClick = { onClick() }) {

    }
}


@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color
): Unit {
}


@Preview(showSystemUi = true, locale = "es")
@Composable
fun RegisterScreenPreview() {
    VFitTheme {
        RegisterScreen()
    }
}