package com.dwh.gamesapp.games_details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.use_cases.favorit_games.IsFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.favorit_games.RemoveFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.game_details.AddFavoriteGameUseCase
import com.dwh.gamesapp.a.presentation.view_model.game_details.GameDetailsUiState
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.games_details.domain.use_cases.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase, // Arreglado
    private val addFavoriteGameUseCase: AddFavoriteGameUseCase,
    private val isFavoriteGameUseCase: IsFavoriteGameUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<UIState<GameDetails?>> = MutableStateFlow(UIState.Loading)
    val uiState: MutableStateFlow<UIState<GameDetails?>> get() = _uiState

    fun getGameDetails(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase(id).collect { resource ->
            _uiState.value = when(resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: GAME_DETAILS",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    UIState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Loading -> UIState.Loading
                is Resource.Success -> UIState.Success(resource.data)
            }
        }
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