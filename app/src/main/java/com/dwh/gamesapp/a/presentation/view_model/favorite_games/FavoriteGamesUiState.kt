package com.dwh.gamesapp.a.presentation.view_model.favorite_games

import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame

sealed class FavoriteGamesUiState {
    data class Success(val data: List<FavoritGame>) : FavoriteGamesUiState()
    data class Error(val errorMessage: String) : FavoriteGamesUiState()
}
