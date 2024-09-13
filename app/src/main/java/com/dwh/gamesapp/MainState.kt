package com.dwh.gamesapp

import com.dwh.gamesapp.core.domain.enums.ThemeValues

data class MainState(
    val favoriteThemeValues: String = ThemeValues.SYSTEM_DEFAULT.title
)
