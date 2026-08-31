package com.example.careplus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = TossBlue,
    onPrimary = TossSurface,
    primaryContainer = TossBlueLight,
    onPrimaryContainer = TossBlueDark,
    secondary = TossGray,
    onSecondary = TossSurface,
    background = TossBackground,
    onBackground = TossBlack,
    surface = TossSurface,
    onSurface = TossBlack,
    surfaceVariant = TossBackground,
    onSurfaceVariant = TossGray,
    outline = TossBorder,
    error = TossRed,
    onError = TossSurface
)

@Composable
fun CarePlusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
