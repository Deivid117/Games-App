package com.dwh.gamesapp.presentation.view_model.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.model.game.NextWeekGames
import com.dwh.gamesapp.domain.model.game.NextWeekGamesResults
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.use_cases.next_week_games.GetNextWeekGamesUseCase
import com.dwh.gamesapp.presentation.view_model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNextWeekGamesUseCase: GetNextWeekGamesUseCase,
    application: Application
): AndroidViewModel(application) {

    private var _uiState: MutableStateFlow<UIState<List<NextWeekGamesResults>?>> = MutableStateFlow(UIState.Loading)
    val uiState: MutableStateFlow<UIState<List<NextWeekGamesResults>?>> get() = _uiState

    fun getNextWeekGames(dates: String) = viewModelScope.launch {
        try {
            getNextWeekGamesUseCase(dates).collect {
                _uiState.value = UIState.Success(it)
            }
        } catch (e: Exception) {
            Log.e("Home_ViewModel",e.message ?: "Error desconocido")
        }
    }

}