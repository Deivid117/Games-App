package com.dwh.gamesapp.home.domain.repository

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.home.domain.model.BestOfTheYear
import com.dwh.gamesapp.home.domain.model.NextWeekGames
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getNextWeekGames(dates: String, platforms: String): Flow<DataState<List<NextWeekGames>>>

    suspend fun getBestOfTheYear(dates: String, ordering: String): Flow<DataState<List<BestOfTheYear>>>
}