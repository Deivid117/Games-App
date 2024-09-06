package com.dwh.gamesapp.a.presentation.view_model.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.domain.use_case.IsUserLoggedInByPreferencesUseCase
import com.dwh.gamesapp.core.domain.use_case.SaveUserSessionFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val isUserLoggedInByPreferencesUseCase: IsUserLoggedInByPreferencesUseCase,
    private val saveUserLoggedInByPreferencesUseCase: SaveUserSessionFromPreferencesUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<WelcomeState> = MutableStateFlow(WelcomeState())
    val uiState: StateFlow<WelcomeState> get() = _uiState.asStateFlow()

    fun isUserLoggedIn() = viewModelScope.launch {
        //saveUserLoggedInByPreferencesUseCase(false)
        isUserLoggedInByPreferencesUseCase().collectLatest { session ->
            _uiState.update { it.copy(isUserLoggedIn = session) }
        }
    }
}