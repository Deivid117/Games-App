package com.dwh.gamesapp.a.presentation.view_model.platforms

import com.dwh.gamesapp.platforms.domain.model.PlatformResults

sealed class PlatformsUiState {
    object Loading: PlatformsUiState()
    data class Success(val data: List<PlatformResults>) : PlatformsUiState()
    data class Error(val errorMessage: String) : PlatformsUiState()
}
