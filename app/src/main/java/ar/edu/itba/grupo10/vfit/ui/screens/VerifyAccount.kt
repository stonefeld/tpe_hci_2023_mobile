package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme

@Composable
fun VerifyAcountScreen() {
    Surface(modifier = Modifier.fillMaxHeight(1f)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight(1f)
        ) {
            Text(
                text = stringResource(R.string.verify),
                fontSize = 40.sp,
                textAlign = TextAlign.Center
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

            Text(
                text = stringResource(id = R.string.code_email),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )

            var code by rememberSaveable { mutableStateOf("") }

            TextField(
                value = code,
                onValueChange = { code = it },
                label = { Text(stringResource(R.string.enter_code)) },
                singleLine = true
            )

            OutlinedButton(
                shape= RoundedCornerShape(size = 12.dp),
                onClick = {},
                modifier = Modifier
                    .padding(30.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.resend),
                        fontSize = 25.sp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}

@Preview(showSystemUi = true, locale = "es")
@Composable
fun VerifyAccountPreview() {
    VFitTheme {
        VerifyAcountScreen()
    }
}