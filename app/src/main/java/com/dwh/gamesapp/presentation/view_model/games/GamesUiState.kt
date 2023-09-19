package com.dwh.gamesapp.presentation.view_model.games

import com.dwh.gamesapp.domain.model.game.GamesResults

sealed class GamesUiState {
    object Loading : GamesUiState()
    data class Success(val data: List<GamesResults>) : GamesUiState()
    data class Error(val errorMessage: String) : GamesUiState()
}
