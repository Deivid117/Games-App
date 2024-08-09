package com.dwh.gamesapp.genres_details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.genres_details.domain.use_cases.GetGenreDetailsUseCase
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.presentation.state.DataState
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

    private var _uiState: MutableStateFlow<DataState<GenreDetails?>> = MutableStateFlow(
        DataState.Loading
    )
    val uiState: StateFlow<DataState<GenreDetails?>> get() = _uiState

    fun getGenreDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getGenreDetailsUseCase(id).collect { resource ->
            _uiState.value = when(resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: GENRE_DETAILS",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    DataState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Loading -> DataState.Loading
                is Resource.Success -> DataState.Success(resource.data)
            }
        }
    }
}