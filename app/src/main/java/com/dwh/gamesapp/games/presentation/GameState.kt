package com.dwh.gamesapp.games.presentation

import com.dwh.gamesapp.games.domain.model.Game

data class GameState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val isRefreshing: Boolean = false,
    val wantedGames: List<Game> = listOf(),
    val searchText: String = "",
    val snackBarMessage: String = "",
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)
