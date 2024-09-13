package com.dwh.gamesapp.games_details.presentation

import com.dwh.gamesapp.games_details.domain.model.GameDetails

data class GameDetailsState(
    val isLoading: Boolean = false,
    val gameDetails: GameDetails? = GameDetails(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)