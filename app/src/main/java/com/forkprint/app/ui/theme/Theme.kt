package com.forkprint.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ForkprintColors = lightColorScheme(
    primary = Color(0xFF6B4F3A),
    onPrimary = Color.White,
    secondary = Color(0xFF8A6F56),
    background = Color(0xFFFFFBF6),
    surface = Color(0xFFFFFBF6),
    surfaceVariant = Color(0xFFF3E6D8),
    onSurface = Color(0xFF251A14),
)

@Composable
fun ForkprintTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ForkprintColors, content = content)
}
