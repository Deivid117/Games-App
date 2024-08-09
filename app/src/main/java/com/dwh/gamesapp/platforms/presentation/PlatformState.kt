package com.dwh.gamesapp.platforms.presentation

import com.dwh.gamesapp.platforms.domain.model.Platform

data class PlatformState(
    val isLoading: Boolean = false,
    val platforms: List<Platform> = listOf(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)