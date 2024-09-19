package com.dwh.gamesapp.signup.presentation

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.domain.use_case.GetFingerPrintEncryptedUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserIdUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserSessionFromPreferencesUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.core.presentation.ui.UiText
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.containsLetter
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.containsNumber
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.containsSpecialCharacter
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.isEmail
import com.dwh.gamesapp.signup.domain.use_case.InsertUserUseCase
import com.dwh.gamesapp.signup.domain.use_case.SaveBiometricEnabledFromPreferencesUseCase
import com.dwh.gamesapp.signup.domain.use_case.UserAlreadyExistsUseCase
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
class SignupViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
    private val saveUserIdFromPreferencesUseCase: SaveUserIdUseCase,
    private val saveUseSessionFromPreferencesUseCase: SaveUserSessionFromPreferencesUseCase,
    private val userAlreadyExistsUseCase: UserAlreadyExistsUseCase,
    private val saveBiometricEnabledFromPreferencesUseCase: SaveBiometricEnabledFromPreferencesUseCase,
    private val getFingerPrintEncryptedUseCase: GetFingerPrintEncryptedUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<SignupState> = MutableStateFlow(SignupState())
    val uiState: StateFlow<SignupState> get() = _uiState.asStateFlow()

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
        _uiState.update { it.copy(fingerPrintToken = fingerPrintToken) }
    }

    fun handleAvatarsModalBottomSheet(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isVisibleAvatarsModalBottomSheet = isVisible)
        }
    }

    fun setUserAvatar(id: Long, image: Int) {
        _uiState.update { currentState ->
            currentState.copy(profileAvatarId = id, profileAvatarImage = image)
        }
    }

    fun onNameChanged(name: String) {
        val nameError = validateName(name)
        _uiState.update { currentState ->
            currentState.copy(name = name, nameError = nameError, formHasErrors = nameError != null)
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

    fun onConfirmPasswordChange(confirmPassword: String) {
        val confirmPasswordError = validateConfirmPassword(confirmPassword)
        _uiState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = confirmPasswordError,
                formHasErrors = confirmPasswordError != null
            )
        }
    }

    private fun validateName(name: String): UiText? {
        return when {
            name.isEmpty() -> UiText.StringResource(R.string.signup_name_error1)
            else -> null
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
            !password.containsLetter() -> UiText.StringResource(R.string.signup_password_error2)
            !password.containsNumber() -> UiText.StringResource(R.string.signup_password_error3)
            !password.containsSpecialCharacter() -> UiText.StringResource(R.string.signup_password_error4)
            password.length < 8 -> UiText.StringResource(R.string.signup_password_error5)
            else -> null
        }
    }

    private fun validateConfirmPassword(confirmPassword: String): UiText? {
        return when {
            confirmPassword != uiState.value.password -> UiText.StringResource(R.string.signup_confirm_password_error1)
            else -> null
        }
    }

    fun validationEmptyFields(): Boolean {
        val hasErrors = uiState.value.run {
            name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        }
        return hasErrors
    }

    private fun formValidation(state: SignupState): Boolean {
        val nameError = validateName(state.name)
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)
        val confirmPasswordError = validateConfirmPassword(state.confirmPassword)
        val hasErrors =
            nameError != null || emailError != null || passwordError != null || confirmPasswordError != null

        _uiState.update { currentState ->
            currentState.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                formHasErrors = hasErrors
            )
        }

        return hasErrors
    }

    fun saveUserSessionFromPreferences(rememberUser: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        saveUseSessionFromPreferencesUseCase(rememberUser)
    }

    fun handleSnackBar(
        isVisible: Boolean,
        lottieAnimation: Int = uiState.value.lottieAnimationSnackBar,
        message: UiText? = null,
        snackBarBorderColor: Color = uiState.value.snackBarBorderColor,
        snackBarContainerColor: Color = uiState.value.snackBarContainerColor,
        snackBarDuration: SnackbarDuration = uiState.value.snackBarDuration
    ) {
        _uiState.update {
            it.copy(
                isSnackBarVisible = isVisible,
                snackBarMessage = message,
                lottieAnimationSnackBar = lottieAnimation,
                snackBarBorderColor = snackBarBorderColor,
                snackBarContainerColor = snackBarContainerColor,
                snackBarDuration = snackBarDuration
            )
        }
    }

    fun userAlreadyExists(
        user: User,
        snackBarContainerColor: Color,
        snackBarBorderColor: Color,
        isBiometricAvailable: Boolean = false
    ) = viewModelScope.launch(Dispatchers.IO) {
        val hasErrors = formValidation(uiState.value)

        if (!hasErrors) {
            userAlreadyExistsUseCase(user.email, user.name).collectLatest { dataState ->
                when (dataState) {
                    is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is DataState.Success -> _uiState.update {
                        if (dataState.data != null) {
                            it.copy(
                                isLoading = false,
                                isSnackBarVisible = true,
                                snackBarMessage = UiText.StringResource(R.string.signup_user_already_exists),
                                lottieAnimationSnackBar = R.raw.broken_heart,
                                snackBarContainerColor = snackBarContainerColor,
                                snackBarBorderColor = snackBarBorderColor,
                                snackBarDuration = SnackbarDuration.Short
                            )
                        } else {
                            validateBiometricsAndSignup(isBiometricAvailable = isBiometricAvailable, user = user)
                            it.copy(isLoading = false)
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

    private fun validateBiometricsAndSignup(isBiometricAvailable: Boolean, user: User) {
        if (isBiometricAvailable && uiState.value.fingerPrintToken == null) handleBiometricDialog(true)
        else signupUser(user)
    }

    fun handleBiometricDialog(isVisible: Boolean) {
        _uiState.update { it.copy(isBiometricDialogVisible = isVisible) }
    }

    fun setBiometricEnabled(isEnabled: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        saveBiometricEnabledFromPreferencesUseCase(isEnabled)
    }

    fun signupUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        insertUserUseCase(user).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> _uiState.update {
                    saveUserIdFromPreferences(dataState.data)
                    it.copy(isLoading = false, isVisibleSuccessDialog = true)
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

    private fun saveUserIdFromPreferences(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        saveUserIdFromPreferencesUseCase(userId)
    }

    fun handleSuccessDialog(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isVisibleSuccessDialog = isVisible)
        }
    }
}