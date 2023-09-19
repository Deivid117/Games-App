package com.dwh.gamesapp.presentation.view_model.platforms

import com.dwh.gamesapp.domain.model.genres.GenresResults
import com.dwh.gamesapp.domain.model.plattform.PlattformResults
import kotlinx.coroutines.flow.Flow

sealed class PlatformsUiState {
    object Loading: PlatformsUiState()
    data class Success(val data: List<PlattformResults>) : PlatformsUiState()
    data class Error(val errorMessage: String) : PlatformsUiState()
}
