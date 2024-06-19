package com.dwh.gamesapp.a.presentation.view_model.genre_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.use_cases.genre_details.GetGenreDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreDetailsViewModel @Inject constructor(
    private val getGenreDetailsUseCase: GetGenreDetailsUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<GenreDetailsUiState> = MutableStateFlow(GenreDetailsUiState.Loading)
    val uiState: StateFlow<GenreDetailsUiState> get() = _uiState

    fun getGenreDetails(id: Int) = viewModelScope.launch {
        _uiState.value = GenreDetailsUiState.Loading
        try {
            getGenreDetailsUseCase(id).collect {
                _uiState.value = GenreDetailsUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = GenreDetailsUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetGenreDetails_ViewModel", (_uiState.value as GenreDetailsUiState.Error).errorMessage)
        }
    }



}