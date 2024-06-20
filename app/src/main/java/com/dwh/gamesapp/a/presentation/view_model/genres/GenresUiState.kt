package com.dwh.gamesapp.a.presentation.view_model.genres

import com.dwh.gamesapp.genres.domain.model.GenresResults

sealed class GenresUiState {
    object Loading: GenresUiState()
    data class Success(val data: List<GenresResults>) : GenresUiState()
    data class Error(val errorMessage: String) : GenresUiState()
}
