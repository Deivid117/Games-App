package com.dwh.gamesapp.a.presentation.view_model.genre_details

import com.dwh.gamesapp.genres_details.domain.model.GenreDetails

sealed class GenreDetailsUiState {
    object Loading: GenreDetailsUiState()
    data class Success(val data: GenreDetails) : GenreDetailsUiState()
    data class Error(val errorMessage: String) : GenreDetailsUiState()}