package com.dwh.gamesapp.presentation.view_model.registration

sealed class RegistrationUiState {
    object Loading : RegistrationUiState()
    data class Success(val message: String) : RegistrationUiState()
    data class Error(val errorMessage: String) : RegistrationUiState()
}