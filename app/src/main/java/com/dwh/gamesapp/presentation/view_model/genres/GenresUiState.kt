package com.dwh.gamesapp.presentation.view_model.genres

import com.dwh.gamesapp.domain.model.genres.GenresResults
import kotlinx.coroutines.flow.Flow

sealed class GenresUiState {
    object Loading: GenresUiState()
    data class Success(val data: List<GenresResults>) : GenresUiState()
    data class Error(val errorMessage: String) : GenresUiState()
}
