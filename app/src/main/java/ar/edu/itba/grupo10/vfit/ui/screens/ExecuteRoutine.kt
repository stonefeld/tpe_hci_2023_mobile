package ar.edu.itba.grupo10.vfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.grupo10.vfit.ui.main.WindowInfo
import ar.edu.itba.grupo10.vfit.ui.main.rememberWindowInfo

@Composable
fun ExecuteRoutineScreen() {
    val windowSize = rememberWindowInfo()
    var image = R.drawable.execute_routine_phone
    if (windowSize.screenWidthInfo == WindowInfo.WindowType.Compact) {
        image = R.drawable.execute_routine_phone
    } else if (windowSize.screenWidthInfo == WindowInfo.WindowType.Expanded){
        image = R.drawable.execute_routine_tablet
    }
    Box(
        modifier = with(Modifier) {
            fillMaxSize()
                .paint(
                    painterResource(id = image),
                    contentScale = ContentScale.FillBounds
                )
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

@Preview(showSystemUi = true, locale = "es", device = "spec:width=411dp,height=891dp")
@Composable
fun PreviewExecuteRoutine1() {
    VFitTheme {
        ExecuteRoutineScreen()
    }
}

@Preview(showSystemUi = true, locale = "es", device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun PreviewExecuteRoutine2() {
    VFitTheme {
        ExecuteRoutineScreen()
    }
}