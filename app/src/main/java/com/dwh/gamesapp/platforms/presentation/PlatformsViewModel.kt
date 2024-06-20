package com.dwh.gamesapp.platforms.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.presentation.view_model.platforms.PlatformsUiState
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.platforms.domain.model.PlatformResults
import com.dwh.gamesapp.platforms.domain.use_cases.GetPlatformsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlatformsViewModel @Inject constructor(
    private val getPlatformsUseCase: GetPlatformsUseCase
): ViewModel() {

    private var _uiStateP: MutableStateFlow<UIState<PlatformResults?>> = MutableStateFlow(UIState.Loading)
    val uiStateP: MutableStateFlow<UIState<PlatformResults?>> get() = _uiStateP

    fun getPlatforms() = viewModelScope.launch(Dispatchers.IO) {
        getPlatformsUseCase().collect { resource ->
            _uiStateP.value = when (resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: PLATFORMS",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    UIState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Loading -> UIState.Loading
                is Resource.Success -> UIState.Success(resource.data)
            }
        }
    }
}