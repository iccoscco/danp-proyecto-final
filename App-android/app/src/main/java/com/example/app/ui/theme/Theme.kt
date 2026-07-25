package com.example.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(

    primary = VerdePrincipal,
    secondary = VerdeSecundario,
    tertiary = NaranjaOferta,

    background = Fondo,
    surface = Blanco,

    onPrimary = Blanco,
    onSecondary = Blanco,
    onTertiary = Blanco,

    onBackground = TextoOscuro,
    onSurface = TextoOscuro

)

private val DarkColorScheme = darkColorScheme(

    primary = VerdeSecundario,
    secondary = VerdePrincipal,
    tertiary = NaranjaOferta

)

@Composable
fun SaveBiteTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )

}