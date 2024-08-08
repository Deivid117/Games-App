package com.dwh.gamesapp.a.presentation.view_model.games

import com.dwh.gamesapp.games.domain.model.Game

sealed class GamesUiState {
    object Loading : GamesUiState()
    data class Success(val data: List<Game>) : GamesUiState()
    data class Error(val errorMessage: String) : GamesUiState()
}
