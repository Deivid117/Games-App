package com.dwh.gamesapp.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.domain.use_case.GetUserIdByPreferencesUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserIdUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserSessionFromPreferencesUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.profile.domain.use_case.GetUserInformationUseCase
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
class ProfileViewModel @Inject constructor(
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val getUserIdByPreferencesUseCase: GetUserIdByPreferencesUseCase,
    private val saveUserIdFromPreferencesUseCase: SaveUserIdUseCase,
    private val saveUseSessionFromPreferencesUseCase: SaveUserSessionFromPreferencesUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> get() = _uiState.asStateFlow()

    fun handleLogoutDialog(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isVisibleLogoutDialog = isVisible)
        }
    }

    fun getUserId() = viewModelScope.launch(Dispatchers.IO) {
        getUserIdByPreferencesUseCase().collectLatest { id ->
            _uiState.update { it.copy(userId = id) }
        }
    }

    fun setUserProfileAvatar(image: Int) {
        _uiState.update { it.copy(userProfileAvatar = image) }
    }

    fun getUserInfo(id: Long) = viewModelScope.launch {
        getUserInformationUseCase(id).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> {
                    if (dataState.data != null) {
                        _uiState.update { it.copy(isLoading = false, user = dataState.data) }
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

    fun logoutUser() = viewModelScope.launch(Dispatchers.IO) {
        saveUserIdFromPreferencesUseCase(0)
        saveUseSessionFromPreferencesUseCase(false)
        _uiState.update { it.copy(isVisibleLogoutDialog = false) }
    }
}