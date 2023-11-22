package ar.edu.itba.grupo10.vfit.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = Color(0xFF7143d1),
    primaryContainer = Color(0xFF7143d1),
    onPrimaryContainer = Color(0xFFFFFFFF),
    secondary = Color(0xFF3B3AE0),
    tertiary = Color(0xFF32ADAD),
    background = Color(0xFF303446),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF303446),
    surfaceVariant = Color(0xFF1E1E2E),
    surfaceTint = Color(0xFFFFFFFF),
)

private val lightColorScheme = lightColorScheme(
    primary = Color(0xFF8B53FF),
    primaryContainer = Color(0xC08B53FF),
    onPrimaryContainer = Color(0xFF000000),
    secondary = Color(0xFF3B3AE0),
    tertiary = Color(0xFF297D7D),
    background = Color(0xFFEFEFEF),
    onBackground = Color(0xFF303446),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFD9D9D9),
    surfaceTint = Color(0xFFFFFFFF)
)

@Composable
fun VFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}