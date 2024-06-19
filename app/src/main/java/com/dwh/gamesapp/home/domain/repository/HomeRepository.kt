package com.dwh.gamesapp.home.domain.repository

import com.dwh.gamesapp.home.domain.model.BestOfTheYearResults
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.home.domain.model.NextWeekGamesResults
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getNextWeekGames(dates: String, platforms: String): Flow<Resource<NextWeekGamesResults>>

    suspend fun getBestOfTheYear(dates: String, ordering: String): Flow<Resource<BestOfTheYearResults>>
}