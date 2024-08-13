package com.dwh.gamesapp.platforms_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms_details.domain.use_cases.GetPlatformDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlatformDetailsViewModel @Inject constructor(
    private val getPlatformDetailsUseCase: GetPlatformDetailsUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<PlatformDetailsState> = MutableStateFlow(PlatformDetailsState())
    val uiState: MutableStateFlow<PlatformDetailsState> get() = _uiState

    fun getPlatformDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getPlatformDetailsUseCase(id).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> _uiState.update { it.copy(isLoading = false, platformDetails = dataState.data) }
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