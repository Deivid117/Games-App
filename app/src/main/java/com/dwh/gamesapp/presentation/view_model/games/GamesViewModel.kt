package com.dwh.gamesapp.presentation.view_model.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dwh.gamesapp.domain.model.game.GamesResults
import com.dwh.gamesapp.domain.use_cases.games.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
): ViewModel() {

    private var _gamesState: MutableStateFlow<PagingData<GamesResults>> = MutableStateFlow(PagingData.empty())
    val gamesState: MutableStateFlow<PagingData<GamesResults>> get() = _gamesState

    /** Esta es la manera de usar el caso de uso*/
    fun getAllGamesPaged() = viewModelScope.launch {
        /** Esta es la forma de usar el invoke*/
        getGamesUseCase()
            .cachedIn(viewModelScope)
            .collect {
                _gamesState.value = it
            }
        /** Esta es la manera de usar el execute */
        // getGamesUseCase.execute()...
    }

}