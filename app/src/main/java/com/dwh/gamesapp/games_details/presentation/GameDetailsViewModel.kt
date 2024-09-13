package com.dwh.gamesapp.games_details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.use_cases.favorit_games.IsFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.favorit_games.RemoveFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.game_details.AddFavoriteGameUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games_details.domain.use_cases.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val addFavoriteGameUseCase: AddFavoriteGameUseCase,
    private val isFavoriteGameUseCase: IsFavoriteGameUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<GameDetailsState> = MutableStateFlow(GameDetailsState())
    val uiState: MutableStateFlow<GameDetailsState> get() = _uiState

    fun getGameDetails(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase(id).collect { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoading = true, isError = false) }
                is DataState.Success -> _uiState.update {
                    it.copy(isLoading = false, isError = false, isRefreshing = false, gameDetails = dataState.data)
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        isRefreshing = false,
                        errorMessage = dataState.errorMessage,
                        errorDescription = dataState.errorDescription,
                        errorCode = dataState.code
                    )
                }
            }
        }
    }

    fun refreshGameDetails(id: Int) {
        _uiState.update { it.copy(isRefreshing = true) }
        getGameDetails(id)
    }

/** TODO: FALTA ARREGLAR ESTOS SERVICIOS */
    fun addFavoriteGame(favoritGame: FavoritGame, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            addFavoriteGameUseCase(favoritGame)
            success(true)
        } catch (e: Exception) {
            success(false)
            Log.e("AddFavoriteGame_ViewModel", e.message ?: "Error desconocido")
        }
    }

    private var _favoriteGame: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val favoriteGame: StateFlow<Boolean> = _favoriteGame

    fun isMyFavoriteGame(id: Int) = viewModelScope.launch {
        _favoriteGame.value = isFavoriteGameUseCase(id)
    }

    fun removerFavoriteGame(id: Int, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
          removeFavoriteGameUseCase(id)
          success(true)
        } catch (e: Exception) {
            success(false)
        }
    }

}