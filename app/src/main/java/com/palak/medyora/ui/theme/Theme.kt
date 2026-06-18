package com.palak.medyora.ui.theme
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


private val MedyoraColorScheme = lightColorScheme(
    primary = Blue600,
    onPrimary = White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue600,
    secondary = Gray600,
    onSecondary = White,
    secondaryContainer = Gray100,
    onSecondaryContainer = Gray700,
    tertiary = Purple600,
    onTertiary         = White,
    background         = Blue50,       // slightly off-white, easier on eyes than pure white
    onBackground       = Gray900,
    surface            = White,
    onSurface          = Gray900,
    surfaceVariant     = Blue50,       // very light blue for card surfaces
    onSurfaceVariant   = Gray600,
    outline            = Blue100,
    outlineVariant     = Gray100,
    error              = Red600,
    onError            = White,
    errorContainer     = Red100,
    onErrorContainer   = Red600,
    surfaceTint = White

)

@Composable
fun MedyoraTheme(
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }

    val colorScheme=MedyoraColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
    