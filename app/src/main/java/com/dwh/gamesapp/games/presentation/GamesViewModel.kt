package com.dwh.gamesapp.games.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.use_cases.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
): ViewModel() {

    private var _uiState: MutableStateFlow<PagingData<Game>> = MutableStateFlow(PagingData.empty())
    val uiState: MutableStateFlow<PagingData<Game>> get() = _uiState

    fun getGames() = viewModelScope.launch(Dispatchers.IO) {
        getGamesUseCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _uiState.value = it
            }
    }
}