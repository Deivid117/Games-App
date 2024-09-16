package com.dwh.gamesapp.games_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.R
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.favorite_games.domain.use_case.IsMyFavoriteGameUseCase
import com.dwh.gamesapp.favorite_games.domain.use_case.DeleteFavoriteGameUseCase
import com.dwh.gamesapp.a.domain.use_cases.game_details.InsertFavoriteGameUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games_details.domain.use_cases.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val insertFavoriteGameUseCase: InsertFavoriteGameUseCase,
    private val isMyFavoriteGameUseCase: IsMyFavoriteGameUseCase,
    private val deleteFavoriteGameUseCase: DeleteFavoriteGameUseCase
): ViewModel() {
    private var _uiState: MutableStateFlow<GameDetailsState> = MutableStateFlow(GameDetailsState())
    val uiState: MutableStateFlow<GameDetailsState> get() = _uiState

    fun getGameDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
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

    fun isMyFavoriteGame(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        isMyFavoriteGameUseCase(gameId).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoadingMyFavorite = true) }
                is DataState.Success -> _uiState.update {
                    it.copy(isLoadingMyFavorite = false, isMyFavoriteGame = dataState.data)
                }
                is DataState.Error -> _uiState.update { it.copy(isLoadingMyFavorite = false) }
            }
        }
    }

    fun insertFavoriteGame(favoriteGame: FavoriteGame) = viewModelScope.launch(Dispatchers.IO) {
        insertFavoriteGameUseCase(favoriteGame).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoadingInsert = true) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoadingInsert = false,
                        isSnackBarVisible = true,
                        isMyFavoriteGame = true,
                        snackBarMessage = "Agregado a favoritos",
                        lottieAnimationSnackBar = R.raw.heart_animation
                    )
                }
                is DataState.Error -> _uiState.update { it.copy(isLoadingInsert = false) }
            }
        }
    }

    fun deleteFavoriteGame(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteFavoriteGameUseCase(gameId).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoadingDelete = true) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoadingDelete = false,
                        isSnackBarVisible = true,
                        isMyFavoriteGame = false,
                        snackBarMessage = "Eliminado de favoritos",
                        lottieAnimationSnackBar = R.raw.broken_heart
                    )
                }
                is DataState.Error -> _uiState.update { it.copy(isLoadingDelete = false) }
            }
        }
    }

    fun hideSnackBar() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(isSnackBarVisible = false) }
    }

    fun refreshGameDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(isRefreshing = true, isSnackBarVisible = false) }
        getGameDetails(id)
        isMyFavoriteGame(id)
    }
}