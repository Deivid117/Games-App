package com.dwh.gamesapp.presentation.view_model.genre_details

import com.dwh.gamesapp.domain.model.genre_details.GenreDetails

sealed class GenreDetailsUiState {
    object Loading: GenreDetailsUiState()
    data class Success(val data: GenreDetails) : GenreDetailsUiState()
    data class Error(val errorMessage: String) : GenreDetailsUiState()}