package com.dwh.gamesapp.favorite_games.presentation

import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame

data class FavoriteGamesState(
    val isLoading: Boolean = true,
    val favoriteGames: List<FavoriteGame> = listOf(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)
