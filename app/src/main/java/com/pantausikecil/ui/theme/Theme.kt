package com.pantausikecil.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryGreen = Color(0xFF23B34A)
val DarkGreen = Color(0xFF128233)
val SoftGreen = Color(0xFFDDF7E5)
val YellowStatus = Color(0xFFFFB020)
val RedStatus = Color(0xFFFF4D5A)
val BlueAction = Color(0xFF1E88E5)
val PageBackground = Color(0xFFFFFFFF)
val SoftGrey = Color(0xFFF4F6F5)
val TextDark = Color(0xFF1E1E1E)

private val PantauColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = DarkGreen,
    background = PageBackground,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun PantauSiKecilTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PantauColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
