package com.dwh.gamesapp.a.presentation.view_model.platform_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.use_cases.platform_details.GetPlatformDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlatformDetailsViewModel @Inject constructor(
    private val getPlatformDetailsUseCase: GetPlatformDetailsUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<PlatformDetailsUiState> = MutableStateFlow(PlatformDetailsUiState.Loading)
    val uiState: MutableStateFlow<PlatformDetailsUiState> get() = _uiState

    fun getPlatformDetails(id: Int) = viewModelScope.launch {
        _uiState.value = PlatformDetailsUiState.Loading
        try {
            getPlatformDetailsUseCase(id).collect {
                _uiState.value = PlatformDetailsUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = PlatformDetailsUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetPlatformDetails_ViewModel", (_uiState.value as PlatformDetailsUiState.Error).errorMessage)
        }
    }

}