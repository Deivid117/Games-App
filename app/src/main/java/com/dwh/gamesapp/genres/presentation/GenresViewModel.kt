package com.dwh.gamesapp.genres.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres.domain.model.GenresResults
import com.dwh.gamesapp.genres.domain.use_cases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<DataState<GenresResults?>> = MutableStateFlow(DataState.Loading)
    val uiState: StateFlow<DataState<GenresResults?>> get() = _uiState

    fun getGenres() = viewModelScope.launch(Dispatchers.IO) {
        getGenresUseCase().collect { resource ->
            _uiState.value = when (resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: GENRES",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    DataState.Error(resource.message ?: "Error desconocido", code = 2)
                }
                is Resource.Loading -> DataState.Loading
                is Resource.Success -> DataState.Success(resource.data)
            }
        }
    }
}