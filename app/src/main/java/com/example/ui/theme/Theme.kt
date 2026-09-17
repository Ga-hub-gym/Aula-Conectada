package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = SkyPrimaryLight,
    onPrimary = Color(0xFF00344F),
    primaryContainer = Color(0xFF004B70),
    onPrimaryContainer = SkyContainer,
    secondary = CyanAccentLight,
    onSecondary = Color(0xFF003644),
    secondaryContainer = Color(0xFF004E60),
    onSecondaryContainer = CyanContainer,
    background = SchoolBackgroundDark,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF334155),
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    outlineVariant = Color(0xFF1E293B),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SkyPrimary,
    onPrimary = Color.White,
    primaryContainer = SkyContainer,
    onPrimaryContainer = OnSkyContainer,
    secondary = CyanAccent,
    onSecondary = Color.White,
    secondaryContainer = CyanContainer,
    onSecondaryContainer = OnCyanContainer,
    background = SchoolBackground,
    surface = WhiteContainer,
    surfaceVariant = Color(0xFFF1F5F9),
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = BorderMedium,
    outlineVariant = BorderLight,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // prioritize school cyan/sky brand palette
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

