package com.valerie.vp_week6.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(
    primary = Blue600,
    onPrimary = Color.White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,
    secondaryContainer = Blue100,
    onSecondaryContainer = Blue900,
    background = ScreenBg,
    surface = Color.White,
    onSurface = Navy,
    onSurfaceVariant = Slate500,
    outline = Slate200,
    error = RedJoined
)

@Composable
fun Vp_week6Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
