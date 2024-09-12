package com.dwh.gamesapp.edit_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.domain.use_case.GetUserIdByPreferencesUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.core.presentation.ui.UiText
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.containsLetter
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.containsNumber
import com.dwh.gamesapp.core.presentation.utils.regex.RegexFunctions.containsSpecialCharacter
import com.dwh.gamesapp.edit_profile.domain.use_case.UpdateUserUseCase
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
class EditProfileViewModel @Inject constructor(
    private val getUserIdByPreferencesUseCase: GetUserIdByPreferencesUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditProfileState> = MutableStateFlow(EditProfileState())
    val uiState: StateFlow<EditProfileState> = _uiState.asStateFlow()

    fun getUserId() = viewModelScope.launch(Dispatchers.IO) {
        getUserIdByPreferencesUseCase().collectLatest { id ->
            _uiState.update { it.copy(userId = id) }
        }
    }

    fun hideSuccessDialog() {
        _uiState.update { currentState ->
            currentState.copy(isVisibleSuccessDialog = false)
        }
    }

    fun handleAvatarsModalBottomSheet(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isVisibleAvatarsModalBottomSheet = isVisible)
        }
    }

    fun onNameChange(name: String) {
        val nameError = validateName(name)
        _uiState.update { currentState ->
            currentState.copy(name = name, nameError = nameError, formHasErrors = nameError != null)
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

    fun setUserName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun setUserAvatar(id: Long, image: Int) {
        _uiState.update { currentState ->
            currentState.copy(profileAvatarId = id, userProfileAvatar = image)
        }
    }

    private fun validateName(name: String): UiText? {
        return when {
            name.isEmpty() -> UiText.StringResource(R.string.edit_profile_name_error1)
            else -> null
        }
    }

    private fun validatePassword(password: String): UiText? {
        return when (password.isNotEmpty()) {
            !password.containsLetter() -> UiText.StringResource(R.string.edit_profile_password_error1)
            !password.containsNumber() -> UiText.StringResource(R.string.edit_profile_password_error2)
            !password.containsSpecialCharacter() -> UiText.StringResource(R.string.edit_profile_password_error3)
            (password.length < 8) -> UiText.StringResource(R.string.edit_profile_password_error4)
            else -> {
                _uiState.update { it.copy(confirmPasswordError = null) }
                null
            }
        }
    }

    private fun validateConfirmPassword(confirmPassword: String): UiText? {
        return when {
            confirmPassword != uiState.value.password -> UiText.StringResource(R.string.edit_profile_confirm_password_error1)
            else -> null
        }
    }

    fun validationPasswordFields(): Boolean {
        val hasErrors = uiState.value.run {
            (password.isNotEmpty() && confirmPassword.isEmpty())
        }

        return hasErrors
    }

    private fun formValidation(state: EditProfileState): Boolean {
        val nameError = validateName(state.name)
        val passwordError = validatePassword(state.password)
        val confirmPasswordError = validateConfirmPassword(state.confirmPassword)
        val hasErrors =
            nameError != null || passwordError != null || confirmPasswordError != null

        _uiState.update { currentState ->
            currentState.copy(
                nameError = nameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                formHasErrors = hasErrors
            )
        }

        return hasErrors
    }

    fun updateUser(
        id: Long,
        name: String,
        password: String,
        profileAvatarId: Long
    ) = viewModelScope.launch {
        val hasErrors = formValidation(uiState.value)

        if (!hasErrors) {
            updateUserUseCase(name, password, profileAvatarId, id).collectLatest { dataState ->
                when (dataState) {
                    is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is DataState.Success -> _uiState.update {
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
    }
}