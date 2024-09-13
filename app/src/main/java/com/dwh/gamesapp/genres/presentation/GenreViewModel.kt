package com.dwh.gamesapp.genres.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres.domain.model.GenreGame
import com.dwh.gamesapp.genres.domain.use_cases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<GenreState> = MutableStateFlow(GenreState())
    val uiState: StateFlow<GenreState> get() = _uiState.asStateFlow()

    fun getGenres() = viewModelScope.launch(Dispatchers.IO) {
        getGenresUseCase().collect { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        genres = dataState.data,
                        isRefreshing = false
                    )
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        errorMessage = dataState.errorMessage,
                        errorDescription = dataState.errorDescription,
                        errorCode = dataState.code
                    )
                }
            }
        }
    }

    fun refreshGenres() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(isRefreshing = true) }
        getGenres()
    }

    fun setGenreGames(games: List<GenreGame>) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(genreGames = games) }
    }
}