package com.dwh.gamesapp.home.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.home.domain.use_cases.GetNextWeekGamesUseCase
import com.dwh.gamesapp.home.domain.use_cases.GetBestOfTheYearUseCase
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.home.domain.model.BestOfTheYearResults
import com.dwh.gamesapp.home.domain.model.NextWeekGamesResults
import com.dwh.gamesapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNextWeekGamesUseCase: GetNextWeekGamesUseCase,
    private val getBestOfTheYearUseCase: GetBestOfTheYearUseCase,
    application: Application
) : AndroidViewModel(application) {

    private var _uiStateNWG: MutableStateFlow<UIState<NextWeekGamesResults?>> =
        MutableStateFlow(UIState.Loading)
    val uiStateNWG: MutableStateFlow<UIState<NextWeekGamesResults?>> get() = _uiStateNWG

    fun getNextWeekGames() = viewModelScope.launch(Dispatchers.IO) {
        getNextWeekGamesUseCase(getDateRange(), Constants.PLATFORMS).collect { resource ->
            _uiStateNWG.value = when (resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: NEXT_WEEK_GAMES",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    UIState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Loading -> UIState.Loading
                is Resource.Success -> UIState.Success(resource.data)
            }
        }
    }

    private var _uiStateBOTY: MutableStateFlow<UIState<BestOfTheYearResults?>> =
        MutableStateFlow(UIState.Loading)
    val uiStateBOTY: MutableStateFlow<UIState<BestOfTheYearResults?>> get() = _uiStateBOTY

    fun getBestOfTheYear() = viewModelScope.launch(Dispatchers.IO) {
        val dateNow = Clock.System.todayIn(TimeZone.currentSystemDefault())

        getBestOfTheYearUseCase("${dateNow.year}-01-01,${dateNow.year}-12-31", Constants.ORDERING).collect { resource ->
            _uiStateBOTY.value = when (resource) {
                is Resource.Loading -> UIState.Loading
                is Resource.Error -> {
                    Log.e(
                        "ERROR: BEST_OF_THE_YEAR",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    UIState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Success -> UIState.Success(resource.data)
            }
        }
    }

    private fun getDateRange(): String {
        val dateNow = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val dateNextWeek = dateNow.plus(7, DateTimeUnit.DAY)
        return "$dateNow,$dateNextWeek"
    }
}