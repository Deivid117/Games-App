package com.dwh.gamesapp.a.presentation.view_model.platform_details

import com.dwh.gamesapp.a.domain.model.platform_details.PlatformDetails

sealed class PlatformDetailsUiState {
    object Loading: PlatformDetailsUiState()
    data class Success(val data: PlatformDetails) : PlatformDetailsUiState()
    data class Error(val errorMessage: String) : PlatformDetailsUiState()}