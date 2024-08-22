package com.dwh.gamesapp.games.presentation.utils

data class GameErrorMessage(
    val errorMessage: String,
    val errorDescription: String = "",
    val code: Int? = null
)
