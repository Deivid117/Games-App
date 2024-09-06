package com.dwh.gamesapp.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.domain.use_case.SaveUserIdUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserSessionFromPreferencesUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.core.presentation.ui.UiText
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.isEmail
import com.dwh.gamesapp.login.domain.use_case.LoginUserUseCase
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
    private val saveUserIdFromPreferencesUseCase: SaveUserIdUseCase,
    private val saveUseSessionFromPreferencesUseCase: SaveUserSessionFromPreferencesUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> get() = _uiState.asStateFlow()

    fun hideSnackBar() {
        _uiState.update { it.copy(isSnackBarVisible = false) }
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

    fun validationEmptyFields() : Boolean {
        val hasErrors = uiState.value.run {
            email.isEmpty() || password.isEmpty()
        }
        return hasErrors
    }

    private fun saveUserIdFromPreferences(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        saveUserIdFromPreferencesUseCase(userId)
    }

    fun saveUserSessionFromPreferences(rememberUser: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        saveUseSessionFromPreferencesUseCase(rememberUser)
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        val hasErrors = formValidation(uiState.value)

        if (!hasErrors) {
            loginUserUseCase(email, password).collectLatest { dataState ->
                when (dataState) {
                    is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is DataState.Success -> {
                        if (dataState.data != null) {
                            saveUserIdFromPreferences(dataState.data.id)
                            _uiState.update { it.copy(isLoading = false, wasUserFound = true) }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    wasUserFound = false,
                                    isSnackBarVisible = true
                                )
                            }
                        }
                    }
                    is DataState.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = dataState.errorMessage,
                            errorDescription = dataState.errorDescription,
                            errorCode = dataState.code
                        )
                    }
                }
            }
        }
    }
}