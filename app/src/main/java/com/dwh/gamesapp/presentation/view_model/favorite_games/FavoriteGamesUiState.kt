package com.dwh.gamesapp.presentation.view_model.favorite_games

import com.dwh.gamesapp.domain.model.favorite_game.FavoritGame

sealed class FavoriteGamesUiState {
    data class Success(val data: List<FavoritGame>) : FavoriteGamesUiState()
    data class Error(val errorMessage: String) : FavoriteGamesUiState()
}
