package ar.edu.itba.grupo10.vfit.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory()),
    onRegisterSuccess: () -> Unit
) {
    val windowSize = rememberWindowInfo()
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.welcome),
                fontSize = 40.sp
            )

            var username by rememberSaveable { mutableStateOf("") }
            var mail by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            var passwordHidden by rememberSaveable { mutableStateOf(true) }



            if(windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded){

                TextField(
                    value = mail,
                    onValueChange = { mail = it },
                    label = { Text(text = stringResource(R.string.enter_mail)) },
                    singleLine = true,
                    modifier = Modifier.width(600.dp)
                )

                Row(
                    modifier = Modifier.padding(20.dp)
                    ) {

                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(text = stringResource(R.string.enter_username)) },
                        singleLine = true,
                        modifier = Modifier.padding(end = 35.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        label = { Text(text = stringResource(R.string.enter_password)) },
                        visualTransformation =
                        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                                val visibilityIcon =
                                    if (passwordHidden) Icons.Default.Visibility else Icons.Filled.VisibilityOff // cambiar iconitos
                                // Please provide localized description for accessibility services
                                val description =
                                    if (passwordHidden) "Show password" else "Hide password"
                                Icon(imageVector = visibilityIcon, contentDescription = description)
                            }
                        }
                    )
                }
            }else{

                TextField(
                    value = mail,
                    onValueChange = { mail = it },
                    label = { Text(text = stringResource(R.string.enter_mail)) },
                    singleLine = true
                )
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(R.string.enter_username)) },
                    singleLine = true,
                    modifier = Modifier.padding(20.dp)
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.enter_password)) },
                    visualTransformation =
                    if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) Icons.Default.Visibility else Icons.Filled.VisibilityOff // cambiar iconitos
                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordHidden) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    }
                )
            }

            Text(
                text = stringResource(R.string.extra_info),
                fontSize = 25.sp
            )
            var firstName by rememberSaveable { mutableStateOf("") }
            var lastName by rememberSaveable { mutableStateOf("") }
            var phone by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current
            val coffeeDrinks = arrayOf("Male", "Female")
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }

            if(windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded){

                Row(){
                    TextField(
                        value = firstName,
                         onValueChange = { firstName = it },
                        label = { Text(text = stringResource(R.string.enter_name)) },
                        singleLine = true,
                        modifier = Modifier.padding(end = 35.dp)
                    )

                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text(text = stringResource(R.string.enter_lastname)) },
                        singleLine = true
                    )
                }

                    Row(
                        modifier=Modifier.padding(top=25.dp)
                    ){
                    TextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(text = stringResource(R.string.enter_phone)) },
                        singleLine = true,
                        modifier = Modifier.padding(end = 35.dp)
                    )


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
                }
            } else{

                TextField(
                    value = firstName,
                    modifier = Modifier.padding(25.dp),
                    onValueChange = { firstName = it },
                    label = { Text(text = stringResource(R.string.enter_name)) },
                    singleLine = true
                )

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(text = stringResource(R.string.enter_lastname)) },
                    singleLine = true
                )


                TextField(
                    value = phone,
                    modifier = Modifier.padding(20.dp),
                    onValueChange = { phone = it },
                    label = { Text(text = stringResource(R.string.enter_phone)) },
                    singleLine = true
                )



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
            }

            val checkedState by rememberSaveable { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = null,
                )
                Text(
                    text = stringResource(R.string.terms),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
            OutlinedButton(onClick = {
                viewModel.register(username, mail, password, onRegisterSuccess)
            }) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 20.sp
                )
            }
        }
    }
}

//@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
//@Composable
//fun RegisterScreenPreview() {
//    VFitTheme {
//        RegisterScreen()
//    }
//}
//
//@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
//@Composable
//fun RegisterScreenPreview2() {
//    VFitTheme {
//        RegisterScreen()
//    }
//}