package com.dwh.gamesapp.a.presentation.view_model.genres

import com.dwh.gamesapp.a.domain.model.genres.GenresResults

sealed class GenresUiState {
    object Loading: GenresUiState()
    data class Success(val data: List<GenresResults>) : GenresUiState()
    data class Error(val errorMessage: String) : GenresUiState()
}
