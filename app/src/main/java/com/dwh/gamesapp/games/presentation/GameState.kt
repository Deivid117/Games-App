package com.dwh.gamesapp.games.presentation

data class GameState(
    val isSnackBarVisible: Boolean = false,
    val snackBarMessage: String = "",
    val isRefreshing: Boolean = false
)
