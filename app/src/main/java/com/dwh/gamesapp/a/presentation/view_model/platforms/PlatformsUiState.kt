package com.dwh.gamesapp.a.presentation.view_model.platforms

import com.dwh.gamesapp.a.domain.model.plattform.PlattformResults

sealed class PlatformsUiState {
    object Loading: PlatformsUiState()
    data class Success(val data: List<PlattformResults>) : PlatformsUiState()
    data class Error(val errorMessage: String) : PlatformsUiState()
}
