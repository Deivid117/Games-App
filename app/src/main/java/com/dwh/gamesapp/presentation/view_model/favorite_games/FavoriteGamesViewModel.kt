package com.dwh.gamesapp.presentation.view_model.favorite_games

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.domain.use_cases.favorit_games.GetFavoriteGamesUseCase
import com.dwh.gamesapp.domain.use_cases.genres.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteGamesViewModel @Inject constructor(
    private val favoriteGamesUseCase: GetFavoriteGamesUseCase
): ViewModel() {

    private var _uiState: MutableStateFlow<FavoriteGamesUiState> = MutableStateFlow(FavoriteGamesUiState.Success(
        listOf()
    ))
    val uiState: MutableStateFlow<FavoriteGamesUiState> get() = _uiState

    fun getAllFavoriteGames() = viewModelScope.launch {
        try {
            favoriteGamesUseCase().collect {
                _uiState.value = FavoriteGamesUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = FavoriteGamesUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetAllFavoriteGames_ViewModel", (_uiState.value as FavoriteGamesUiState.Error).errorMessage)
        }
    }
}