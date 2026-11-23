package com.example.edinburghtransportation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalAppDarkTheme = compositionLocalOf { false }
private val LightColorScheme = lightColorScheme(

    primary = EdinburghBlue,
    onPrimary = LightSurface,

    primaryContainer = EdinburghBlueLight,
    onPrimaryContainer = LightSurface,

    secondary = EdinburghBlueLight,
    onSecondary = LightSurface,

    tertiary = EdinburghYellow,
    onTertiary = PrimaryText,

    background = LightBackground,
    onBackground = PrimaryText,

    surface = LightSurface,
    onSurface = PrimaryText,

    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = SecondaryText,

    error = EdinburghRed,
    onError = LightSurface,

    outline = BorderColor
)

private val DarkColorScheme = darkColorScheme(

    primary = EdinburghBlueLight,
    onPrimary = DarkPrimaryText,

    primaryContainer = EdinburghBlue,
    onPrimaryContainer = DarkPrimaryText,

    secondary = EdinburghBlueLight,
    onSecondary = DarkPrimaryText,

    tertiary = EdinburghYellow,
    onTertiary = PrimaryText,

    background = DarkBackground,
    onBackground = DarkPrimaryText,

    surface = DarkSurface,
    onSurface = DarkPrimaryText,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkSecondaryText,

    error = EdinburghRed,
    onError = DarkPrimaryText,

    outline = ColorOutline
)

@Composable
fun EdinburghTransportationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    CompositionLocalProvider(
        LocalAppDarkTheme provides darkTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}