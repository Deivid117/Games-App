package com.dwh.gamesapp.a.presentation.view_model.genres

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.use_cases.genres.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<GenresUiState> = MutableStateFlow(GenresUiState.Loading)
    val uiState: MutableStateFlow<GenresUiState> get() = _uiState

    fun getAllGenres() = viewModelScope.launch {
        _uiState.value = GenresUiState.Loading
        try {
            getGenresUseCase().collect {
                _uiState.value = GenresUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = GenresUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetAllGenres_ViewModel", (_uiState.value as GenresUiState.Error).errorMessage)
        }
    }
}