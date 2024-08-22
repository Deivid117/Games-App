package com.dwh.gamesapp.games.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.use_cases.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private var _pagingGames: MutableStateFlow<PagingData<Game>> = MutableStateFlow(PagingData.empty())
    val pagingGames: StateFlow<PagingData<Game>> get() = _pagingGames.asStateFlow()

    private var _uiState: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> get() = _uiState.asStateFlow()

    fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collectLatest { games ->
                _pagingGames.value = games
            }
    }

    fun showSnackBar(snackBarMessage: String) {
        _uiState.update { currentState ->
            currentState.copy(
                isSnackBarVisible = true,
                snackBarMessage = snackBarMessage
            )
        }
    }

    fun hideSnackBar() {
        _uiState.update { currentState ->
            currentState.copy(
                isSnackBarVisible = false,
                snackBarMessage = ""
            )
        }
    }

    fun refreshList(onRefresh: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update { it.copy(isRefreshing = true) }
        onRefresh()
        delay(1000)
        _uiState.update { it.copy(isRefreshing = false) }
    }
}