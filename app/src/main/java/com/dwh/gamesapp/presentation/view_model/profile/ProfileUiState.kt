package com.dwh.gamesapp.presentation.view_model.profile

import com.dwh.gamesapp.domain.model.user.User

sealed class ProfileUiState {
    data class Success(val data: User) : ProfileUiState()
    data class Error(val errorMessage: String) : ProfileUiState()
}