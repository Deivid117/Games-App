package com.dwh.gamesapp.a.presentation.view_model.profile

import com.dwh.gamesapp.a.domain.model.user.User

sealed class ProfileUiState {
    data class Success(val data: User) : ProfileUiState()
    data class Error(val errorMessage: String) : ProfileUiState()
}