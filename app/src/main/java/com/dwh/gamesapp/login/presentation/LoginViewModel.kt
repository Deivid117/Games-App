package com.dwh.gamesapp.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.domain.use_case.GetFingerPrintEncryptedUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserIdUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserSessionFromPreferencesUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.core.presentation.ui.UiText
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.isEmail
import com.dwh.gamesapp.login.domain.use_case.IsBiometricEnabledUseCase
import com.dwh.gamesapp.login.domain.use_case.LoginUserUseCase
import com.dwh.gamesapp.login.domain.use_case.LoginUserWithFingerPrintTokenUseCase
import com.dwh.gamesapp.login.domain.use_case.UpdateUserFingerPrintTokenUseCase
import com.dwh.gamesapp.signup.domain.use_case.SaveBiometricEnabledFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val loginUserWithFingerPrintTokenUseCase: LoginUserWithFingerPrintTokenUseCase,
    private val saveUserIdFromPreferencesUseCase: SaveUserIdUseCase,
    private val saveUserSessionFromPreferencesUseCase: SaveUserSessionFromPreferencesUseCase,
    private val isBiometricEnabledUseCase: IsBiometricEnabledUseCase,
    private val saveBiometricEnabledFromPreferencesUseCase: SaveBiometricEnabledFromPreferencesUseCase,
    private val updateUserFingerPrintTokenUseCase: UpdateUserFingerPrintTokenUseCase,
    private val getFingerPrintEncryptedUseCase: GetFingerPrintEncryptedUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> get() = _uiState.asStateFlow()

    fun checkIfBiometricLoginEnabled() {
        viewModelScope.launch(Dispatchers.IO) {
            val isBiometricEnabled = isBiometricEnabledUseCase()
            if (isBiometricEnabled) {
                _uiState.update { it.copy(isBiometricPromptVisible = true) }
            }
        }
    }

    fun getFingerPrintEncrypted(
        context: Context,
        filename: String,
        prefKey: String,
        mode: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        val fingerPrintToken = getFingerPrintEncryptedUseCase(
            context = context,
            filename = filename,
            mode = mode,
            prefKey = prefKey
        )
        _uiState.update { it.copy(
            fingerPrintToken = fingerPrintToken,
            showBiometricButton = fingerPrintToken != null
        ) }
    }

    fun loginUserWithFingerPrintToken(token: EncryptedData) = viewModelScope.launch(Dispatchers.IO) {
        loginUserWithFingerPrintTokenUseCase(token).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoadingUserWithToken = true) }
                is DataState.Success -> {
                    if (dataState.data != null) {
                        saveUserIdFromPreferences(dataState.data.id)
                        saveUserSessionFromPreferences(true)
                        _uiState.update { it.copy(isLoadingUserWithToken = false, wasUserFound = true) }
                    } else {
                        _uiState.update {
                            it.copy(isLoadingUserWithToken = false, wasUserFound = false, isSnackBarVisible = true)
                        }
                    }
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoadingUserWithToken = false,
                        errorMessageUserWithToken = dataState.errorMessage,
                        errorDescriptionUserWithToken = dataState.errorDescription,
                        errorCodeUserWithToken = dataState.code
                    )
                }
            }
        }
    }

    fun onEmailChanged(email: String) {
        val emailError = validateEmail(email)
        _uiState.update { currentState ->
            currentState.copy(email = email, emailError = emailError, formHasErrors = emailError != null)
        }
    }

    fun onPasswordChange(password: String) {
        val passwordError = validatePassword(password)
        _uiState.update { currentState ->
            currentState.copy(password = password, passwordError = passwordError, formHasErrors = passwordError != null)
        }
    }

    private fun validateEmail(email: String): UiText? {
        return when {
            email.isEmpty() -> UiText.StringResource(R.string.signup_email_error1)
            !email.isEmail() -> UiText.StringResource(R.string.signup_email_error2)
            else -> null
        }
    }

    private fun validatePassword(password: String): UiText? {
        return when {
            password.isEmpty() -> UiText.StringResource(R.string.signup_password_error1)
            else -> null
        }
    }

    fun saveUserSessionFromPreferences(rememberUser: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        saveUserSessionFromPreferencesUseCase(rememberUser)
    }

    fun validationEmptyFields(): Boolean {
        val hasErrors = uiState.value.run {
            email.isEmpty() || password.isEmpty()
        }
        return hasErrors
    }

    private fun formValidation(state: LoginState): Boolean {
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)
        val hasErrors =
            emailError != null || passwordError != null

        _uiState.update { currentState ->
            currentState.copy(
                emailError = emailError,
                passwordError = passwordError,
                formHasErrors = hasErrors
            )
        }
        return hasErrors
    }

    fun loginUser(email: String, password: String, isBiometricAvailable: Boolean) = viewModelScope.launch {
        val hasErrors = formValidation(uiState.value)

        if (!hasErrors) {
            loginUserUseCase(email, password).collectLatest { dataState ->
                when (dataState) {
                    is DataState.Loading -> _uiState.update { it.copy(isLoadingUser = true) }
                    is DataState.Success -> _uiState.update {
                        if (dataState.data != null) {
                            saveUserIdFromPreferences(dataState.data.id)
                            validateBiometricsAndLogin(
                                isBiometricAvailable = isBiometricAvailable,
                                userIdFounded = dataState.data.id
                            )
                            it.copy(isLoadingUser = false)
                        } else {
                            it.copy(isLoadingUser = false, wasUserFound = false, isSnackBarVisible = true)
                        }
                    }
                    is DataState.Error -> _uiState.update {
                        it.copy(
                            isLoadingUser = false,
                            errorMessageUser = dataState.errorMessage,
                            errorDescriptionUser = dataState.errorDescription,
                            errorCodeUser = dataState.code
                        )
                    }
                }
            }
        }
    }

    private fun saveUserIdFromPreferences(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        saveUserIdFromPreferencesUseCase(userId)
    }

    private fun validateBiometricsAndLogin(isBiometricAvailable: Boolean, userIdFounded: Long) {
        if (isBiometricAvailable) fingerPrintTokenIsNull(userIdFounded)
        else _uiState.update { it.copy(wasUserFound = true) }
    }

    private fun fingerPrintTokenIsNull(userIdFounded: Long) {
        if (uiState.value.fingerPrintToken == null) {
            _uiState.update { it.copy(isBiometricDialogVisible = true, userIdFounded = userIdFounded) }
        } else _uiState.update { it.copy(wasUserFound = true) }
    }

    fun hideBiometricDialog() {
        _uiState.update { it.copy(isBiometricDialogVisible = false) }
    }

    fun setBiometricEnabled(isEnabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        saveBiometricEnabledFromPreferencesUseCase(isEnabled)
    }

    fun updateUser(token: EncryptedData, userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        updateUserFingerPrintTokenUseCase(token, userId).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoadingUpdateUser = true) }
                is DataState.Success -> _uiState.update { it.copy(isLoadingUpdateUser = false, wasUserFound = true) }
                is DataState.Error -> _uiState.update { it.copy(isLoadingUpdateUser = false) }
            }
        }
    }

    fun hideSnackBar() {
        _uiState.update { it.copy(isSnackBarVisible = false) }
    }
}