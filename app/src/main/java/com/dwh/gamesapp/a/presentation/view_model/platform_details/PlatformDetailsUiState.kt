package com.dwh.gamesapp.a.presentation.view_model.platform_details

import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails

sealed class PlatformDetailsUiState {
    object Loading: PlatformDetailsUiState()
    data class Success(val data: PlatformDetails) : PlatformDetailsUiState()
    data class Error(val errorMessage: String) : PlatformDetailsUiState()}