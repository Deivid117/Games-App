package com.dwh.gamesapp.games.presentation.utils

data class ErrorMessage(
    val errorMessage: String,
    val errorDescription: String = "",
    val code: Int? = null
)
