package com.dwh.gamesapp.favorite_games.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.favorite_games.domain.use_case.GetFavoriteGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteGamesViewModel @Inject constructor(
    private val favoriteGamesUseCase: GetFavoriteGamesUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<FavoriteGamesState> = MutableStateFlow(FavoriteGamesState())
    val uiState: StateFlow<FavoriteGamesState> get() = _uiState.asStateFlow()

    fun getAllFavoriteGames() = viewModelScope.launch(Dispatchers.IO) {
        favoriteGamesUseCase().collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                is DataState.Success -> _uiState.update {
                    it.copy(isLoading = false, favoriteGames = dataState.data)
                }
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