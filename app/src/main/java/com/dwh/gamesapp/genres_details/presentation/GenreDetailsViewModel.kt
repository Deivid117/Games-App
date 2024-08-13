package com.dwh.gamesapp.genres_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.genres_details.domain.use_cases.GetGenreDetailsUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreDetailsViewModel @Inject constructor(
    private val getGenreDetailsUseCase: GetGenreDetailsUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<GenreDetailsState> = MutableStateFlow(GenreDetailsState())
    val uiState: MutableStateFlow<GenreDetailsState> get() = _uiState

    fun getGenreDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getGenreDetailsUseCase(id).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> _uiState.update { it.copy(isLoading = false, genreDetails = dataState.data) }
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