package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.grupo10.vfit.R
import ar.edu.itba.grupo10.vfit.ui.theme.VFitTheme
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter

@Composable
fun ExecuteRoutine() {
    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.execute_routine),
                    contentScale = ContentScale.FillBounds)
        })
    {
        Column {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(30.dp)
                )
            }
            Surface(
                modifier = Modifier.fillMaxSize(1f),
                color = Color.Transparent
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = "Abdominales",
                            fontSize = 40.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = "3 x 25 reps |  60’’",
                            fontSize = 35.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.skip),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = "Proximo:",
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = "Flexiones de Brazo",
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            text = "4 x 20 reps |  60’’",
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Default,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, locale = "es")
@Composable
fun PreviewExecuteRoutine() {
    VFitTheme {
        ExecuteRoutine()
    }
}