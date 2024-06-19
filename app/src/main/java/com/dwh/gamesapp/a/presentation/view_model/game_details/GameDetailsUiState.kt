package com.dwh.gamesapp.a.presentation.view_model.game_details

import com.dwh.gamesapp.a.domain.model.game_details.GameDetails

sealed class GameDetailsUiState {
    object Loading: GameDetailsUiState()
    data class Success(val data: GameDetails) : GameDetailsUiState()
    data class Error(val errorMessage: String) : GameDetailsUiState()}