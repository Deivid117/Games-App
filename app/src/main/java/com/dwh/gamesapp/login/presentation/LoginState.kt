package com.dwh.gamesapp.login.presentation

import com.dwh.gamesapp.core.presentation.ui.UiText


data class LoginState(
    val isLoading: Boolean = false,
    val formHasErrors: Boolean = false,
    val wasUserFound: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val isCheckBoxChecked: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val userId: Long = 0L,
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)
