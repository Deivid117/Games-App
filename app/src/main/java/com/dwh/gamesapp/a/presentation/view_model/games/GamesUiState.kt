package com.dwh.gamesapp.a.presentation.view_model.games

import com.dwh.gamesapp.a.domain.model.game.GamesResults

sealed class GamesUiState {
    object Loading : GamesUiState()
    data class Success(val data: List<GamesResults>) : GamesUiState()
    data class Error(val errorMessage: String) : GamesUiState()
}
