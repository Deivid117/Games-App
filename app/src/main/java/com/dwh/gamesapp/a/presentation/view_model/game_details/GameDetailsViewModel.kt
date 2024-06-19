package com.dwh.gamesapp.a.presentation.view_model.game_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.use_cases.favorit_games.IsFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.favorit_games.RemoveFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.game_details.AddFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.game_details.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val addFavoriteGameUseCase: AddFavoriteGameUseCase,
    private val isFavoriteGameUseCase: IsFavoriteGameUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<GameDetailsUiState> = MutableStateFlow(GameDetailsUiState.Loading)
    val uiState: MutableStateFlow<GameDetailsUiState> get() = _uiState

    fun getGameDetails(id: Int) = viewModelScope.launch {
        _uiState.value = GameDetailsUiState.Loading
        try {
            getGameDetailsUseCase(id).collect {
                _uiState.value = GameDetailsUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = GameDetailsUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetGameDetails_ViewModel", (_uiState.value as GameDetailsUiState.Error).errorMessage)
        }
    }

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