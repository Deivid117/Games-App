package com.dwh.gamesapp.platforms_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms_details.domain.use_cases.GetPlatformDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlatformDetailsViewModel @Inject constructor(
    private val getPlatformDetailsUseCase: GetPlatformDetailsUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<DataState<PlatformDetails?>> = MutableStateFlow(
        DataState.Loading
    )
    val uiState: MutableStateFlow<DataState<PlatformDetails?>> get() = _uiState

    fun getPlatformDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getPlatformDetailsUseCase(id).collect { resource ->
            /*_uiState.value = when(resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: PLATFORM_DETAILS",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    UIState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Loading -> UIState.Loading
                is Resource.Success -> UIState.Success(resource.data)
            }*/
        }
    }
}