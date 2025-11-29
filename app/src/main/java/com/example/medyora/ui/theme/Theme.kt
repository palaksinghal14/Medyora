package com.example.medyora.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue500,
    onPrimary = Gray900,
    primaryContainer = Blue600,
    onPrimaryContainer = Blue100,
    secondary = Gray700,
    onSecondary = Gray100,
    secondaryContainer = Gray800,
    onSecondaryContainer = Gray200,
    tertiary = Blue200,
    onTertiary = Blue600,
    background = Gray900,
    onBackground = Gray100,
    surface = Gray900,
    onSurface = Gray100,
    surfaceVariant = Gray800,
    onSurfaceVariant = Gray400,
    outline = Gray600,
    outlineVariant = Gray700,
    error = Red600,
    onError = White,
    errorContainer = Red50,
    onErrorContainer = Red600
)

private val LightColorScheme = lightColorScheme(
    primary = Blue600,
    onPrimary = White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue600,
    secondary = Gray100,
    onSecondary = Gray800,
    secondaryContainer = Gray50,
    onSecondaryContainer = Gray700,
    tertiary = Blue50,
    onTertiary = Blue600,
    background = White,
    onBackground = Gray900,
    surface = White,
    onSurface = Gray900,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray600,
    outline = Gray300,
    outlineVariant = Gray200,
    error = Red600,
    onError = White,
    errorContainer = Red50,
    onErrorContainer = Red600


)

@Composable
fun MedyoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
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