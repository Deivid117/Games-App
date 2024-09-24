package com.dwh.gamesapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.domain.use_case.GetFavoriteThemeByPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFavoriteThemeByPreferencesUseCase: GetFavoriteThemeByPreferencesUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> get() = _uiState.asStateFlow()

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    init {
        getFavoriteTheme()

        viewModelScope.launch {
            //delay(3000L)
            _isReady.value = true
        }
    }

    private fun getFavoriteTheme() = viewModelScope.launch(Dispatchers.IO) {
        getFavoriteThemeByPreferencesUseCase().collectLatest { theme ->
            _uiState.update { it.copy(favoriteThemeValues = theme) }
        }
    }
}