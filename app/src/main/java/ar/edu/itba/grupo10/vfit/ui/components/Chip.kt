package ar.edu.itba.grupo10.vfit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chip(text: String, icon: ImageVector) {
    Column(modifier = Modifier.padding(2.dp)) {
        Surface(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .size(10.dp)
                )
                Text(
                    text = text,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}