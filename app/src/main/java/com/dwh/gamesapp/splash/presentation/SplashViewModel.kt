package com.dwh.gamesapp.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.domain.use_case.IsUserLoggedInByPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserLoggedInByPreferencesUseCase: IsUserLoggedInByPreferencesUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val uiState: StateFlow<SplashState> get() = _uiState.asStateFlow()

    fun isUserLoggedIn() = viewModelScope.launch {
        isUserLoggedInByPreferencesUseCase().collectLatest { session ->
            _uiState.update { it.copy(isUserLoggedIn = session) }
        }
    }
}