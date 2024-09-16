package com.dwh.gamesapp.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.home.domain.use_cases.GetNextWeekGamesUseCase
import com.dwh.gamesapp.home.domain.use_cases.GetBestOfTheYearUseCase
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.platforms.domain.use_cases.GetPlatformsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
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
    private val getBestOfTheYearUseCase: GetBestOfTheYearUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> get() = _uiState.asStateFlow()

    fun getNextWeekGames() = viewModelScope.launch(Dispatchers.IO) {
        getNextWeekGamesUseCase(
            dates = getDateRange(),
            platforms = Constants.PLATFORMS
        ).collectLatest { dataState ->
            when (dataState) {
                is DataState.Loading -> _uiState.update { it.copy(isLoadingNWG = true, isErrorNWG = false) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoadingNWG = false,
                        isErrorNWG = false,
                        nextWeekGames = dataState.data
                    )
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoadingNWG = false,
                        isErrorNWG = true,
                        errorMessageNWG = dataState.errorMessage,
                        errorDescriptionNWG = dataState.errorDescription,
                        errorCodeNWG = dataState.code
                    )
                }
            }
        }
    }

    fun getBestOfTheYear() = viewModelScope.launch(Dispatchers.IO) {
        val dateNow = Clock.System.todayIn(TimeZone.currentSystemDefault())

        getBestOfTheYearUseCase(
            dates = "${dateNow.year}-01-01,${dateNow.year}-12-31",
            ordering = Constants.ORDERING
        ).collectLatest { dataState ->
            when (dataState) {
                DataState.Loading -> _uiState.update { it.copy(isLoadingBOTY = true, isErrorBOTY = false) }
                is DataState.Success -> _uiState.update {
                    it.copy(
                        isLoadingBOTY = false,
                        isErrorBOTY = false,
                        bestOfTheYear = dataState.data
                    )
                }
                is DataState.Error -> _uiState.update {
                    it.copy(
                        isLoadingBOTY = false,
                        isErrorBOTY = true,
                        errorMessageBOTY = dataState.errorMessage,
                        errorDescriptionBOTY = dataState.errorDescription,
                        errorCodeBOTY = dataState.code
                    )
                }
            }
        }
    }

    fun refreshNextWeekGames() = viewModelScope.launch(Dispatchers.IO) {
        getNextWeekGames()
    }

    fun refreshBestOfTheYear() = viewModelScope.launch(Dispatchers.IO) {
        getBestOfTheYear()
    }

    private fun getDateRange(): String {
        val dateNow = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val dateNextWeek = dateNow.plus(7, DateTimeUnit.DAY)
        return "$dateNow,$dateNextWeek"
    }
}