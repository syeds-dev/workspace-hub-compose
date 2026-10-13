package com.syedsubahani.workspacehub.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WorkspaceHubColors = lightColorScheme(
    primary = BrandRed,
    onPrimary = Color.White,
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFAFAFA),
)

@Composable
fun WorkspaceHubTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = WorkspaceHubColors, content = content)
}
