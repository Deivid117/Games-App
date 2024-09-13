package com.dwh.gamesapp.platforms.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.platforms.domain.use_cases.GetPlatformsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlatformViewModel @Inject constructor(
    private val getPlatformsUseCase: GetPlatformsUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<PlatformState> = MutableStateFlow(PlatformState())
    val uiState: StateFlow<PlatformState> get() = _uiState.asStateFlow()

    fun getPlatforms() = viewModelScope.launch(Dispatchers.IO) {
        getPlatformsUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        platforms = dataState.data
                    )
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = dataState.errorMessage,
                        errorDescription = dataState.errorDescription,
                        errorCode = dataState.code
                    )
                }
            }
        }
    }

    fun refreshPlatforms() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(isRefreshing = true) }
        getPlatforms()
    }

    fun setPlatformGames(games: List<PlatformGame>) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(platformGames = games) }
    }
}