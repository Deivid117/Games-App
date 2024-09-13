package com.dwh.gamesapp.core.presentation.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.dwh.gamesapp.core.domain.enums.ThemeValues
import com.dwh.gamesapp.core.presentation.theme.LocalTheme

@Composable
fun isDarkThemeEnabled(): Boolean {
    val currentTheme = LocalTheme.current.defaultTheme
    return when (currentTheme) {
        ThemeValues.DARK_MODE.title -> true
        ThemeValues.LIGHT_MODE.title -> false
        ThemeValues.SYSTEM_DEFAULT.title -> isSystemInDarkTheme() // Si el sistema es el que define el tema
        else -> isSystemInDarkTheme() // Caso por defecto
    }
}