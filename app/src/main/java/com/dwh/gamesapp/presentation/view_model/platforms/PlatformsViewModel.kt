package com.dwh.gamesapp.presentation.view_model.platforms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.use_cases.platforms.GetPlatformsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlatformsViewModel @Inject constructor(
    private val getPlatformsUseCase: GetPlatformsUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<PlatformsUiState> = MutableStateFlow(PlatformsUiState.Loading)
    val uiState: MutableStateFlow<PlatformsUiState> get() = _uiState

    fun getAllPlatforms() = viewModelScope.launch {
        _uiState.value = PlatformsUiState.Loading
        try {
            getPlatformsUseCase().collect {
                _uiState.value = PlatformsUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = PlatformsUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetAllPlatforms_ViewModel", (_uiState.value as PlatformsUiState.Error).errorMessage)
        }
    }
}