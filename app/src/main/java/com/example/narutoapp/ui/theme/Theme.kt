package com.example.narutoapp.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    primary = DarkPrimaryText,
    onPrimary = DarkBackground,
    onBackground = DarkPrimaryText,
    secondary = DarkSecondaryText,
    onSurface = DarkPrimaryText,
    tertiary = DarkFavoriteActive,
    outline = DarkFavoriteInactive
)

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    primary = LightPrimaryText,
    onPrimary = LightBackground,
    onBackground = LightPrimaryText,
    secondary = LightSecondaryText,
    onSurface = LightPrimaryText,
    tertiary = LightFavoriteActive,
    outline = LightFavoriteInactive
)

@Composable
fun NarutoAppTheme(
//    darkTheme: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}