package com.dwh.gamesapp.a.presentation.view_model.genre_details

import com.dwh.gamesapp.a.domain.model.genre_details.GenreDetails

sealed class GenreDetailsUiState {
    object Loading: GenreDetailsUiState()
    data class Success(val data: GenreDetails) : GenreDetailsUiState()
    data class Error(val errorMessage: String) : GenreDetailsUiState()}