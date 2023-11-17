package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Transgender
import androidx.compose.material.icons.rounded.Mail
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.grupo10.vfit.ui.main.MainViewModel
import ar.edu.itba.grupo10.vfit.utils.getViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    //viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(1f)
                .padding(15.dp)
        ) {

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(start = 25.dp)
                    .fillMaxWidth(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .width(width = 150.dp)
                        .height(height = 150.dp)
                        .padding(25.dp)
                        .clip(shape = RoundedCornerShape(size = 25.dp)),
                    painter = painterResource(id = R.drawable.guest),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = "Name and lastname",
                    fontSize = 36.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )

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
                    Icons.Rounded.Person,
                    contentDescription = stringResource(id = R.string.enter_mail)
                )
                Text(
                    text = "Username",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
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
                    Icons.Rounded.Mail,
                    contentDescription = stringResource(id = R.string.enter_mail)
                )
                Text(
                    text = "Email",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
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
                    Icons.Rounded.Phone,
                    contentDescription = stringResource(id = R.string.enter_mail)
                )
                Text(
                    text = "Phone",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
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
                    Icons.Rounded.Cake,
                    contentDescription = stringResource(id = R.string.enter_mail)
                )
                Text(
                    text = "Birthday",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 10.dp),
                )
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
}


@Preview(showSystemUi = true, locale = "es")
@Composable
fun ProfileScreenPreview() {
    VFitTheme {
        ProfileScreen()
    }
}