package com.dwh.gamesapp.home.presentation

import com.dwh.gamesapp.home.domain.model.BestOfTheYear
import com.dwh.gamesapp.home.domain.model.NextWeekGames

data class HomeState(
    val isLoadingNWG: Boolean = true,
    val isLoadingBOTY: Boolean = true,
    val isErrorNWG: Boolean = false,
    val isErrorBOTY: Boolean = false,
    val nextWeekGames: List<NextWeekGames> = listOf(),
    val bestOfTheYear: List<BestOfTheYear> = listOf(),
    val errorMessageNWG: String = "",
    val errorDescriptionNWG: String = "",
    val errorCodeNWG: Int? = 0,
    val errorMessageBOTY: String = "",
    val errorDescriptionBOTY: String = "",
    val errorCodeBOTY: Int? = 0
)
