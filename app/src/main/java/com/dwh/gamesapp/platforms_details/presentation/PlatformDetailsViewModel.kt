package com.dwh.gamesapp.platforms_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms_details.domain.use_cases.GetPlatformDetailsUseCase
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
class PlatformDetailsViewModel @Inject constructor(
    private val getPlatformDetailsUseCase: GetPlatformDetailsUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<PlatformDetailsState> = MutableStateFlow(PlatformDetailsState())
    val uiState: StateFlow<PlatformDetailsState> get() = _uiState.asStateFlow()

    fun getPlatformDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getPlatformDetailsUseCase(id).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true, isError = false) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        isError = false,
                        platformDetails = dataState.data
                    )
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        isRefreshing = false,
                        errorMessage = dataState.errorMessage,
                        errorDescription = dataState.errorDescription,
                        errorCode = dataState.code
                    )
                }
            }
        }
    }

    fun refreshPlatformDetails(id: Int) {
        _uiState.update { it.copy(isRefreshing = true) }
        getPlatformDetails(id)
    }
}