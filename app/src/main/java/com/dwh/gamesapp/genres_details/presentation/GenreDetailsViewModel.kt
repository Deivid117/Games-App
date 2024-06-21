package com.dwh.gamesapp.genres_details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.genres_details.domain.use_cases.GetGenreDetailsUseCase
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreDetailsViewModel @Inject constructor(
    private val getGenreDetailsUseCase: GetGenreDetailsUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<UIState<GenreDetails?>> = MutableStateFlow(
        UIState.Loading
    )
    val uiState: StateFlow<UIState<GenreDetails?>> get() = _uiState

    fun getGenreDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getGenreDetailsUseCase(id).collect { resource ->
            _uiState.value = when(resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: GENRE_DETAILS",
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