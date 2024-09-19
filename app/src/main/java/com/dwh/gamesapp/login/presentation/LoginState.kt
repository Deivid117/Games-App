package com.dwh.gamesapp.login.presentation

import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.presentation.ui.UiText


data class LoginState(
    val isLoadingUser: Boolean = false,
    val isLoadingUserWithToken: Boolean = false,
    val isLoadingUpdateUser: Boolean = false,
    val formHasErrors: Boolean = false,
    val wasUserFound: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val isCheckBoxChecked: Boolean = false,
    val isBiometricPromptVisible: Boolean = false,
    val isBiometricDialogVisible: Boolean = false,
    val showBiometricButton: Boolean = false,
    val userIdFounded: Long = 0L,
    val fingerPrintToken: EncryptedData? = null,
    val email: String = "",
    val password: String = "",
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val errorMessageUser: String = "",
    val errorDescriptionUser: String = "",
    val errorCodeUser: Int? = 0,
    val errorMessageUserWithToken: String = "",
    val errorDescriptionUserWithToken: String = "",
    val errorCodeUserWithToken: Int? = 0
)
