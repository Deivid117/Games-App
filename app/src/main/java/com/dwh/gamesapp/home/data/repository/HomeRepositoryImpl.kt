package com.dwh.gamesapp.home.data.repository

import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.home.domain.repository.HomeRepository
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.home.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.home.domain.model.BestOfTheYear
import com.dwh.gamesapp.home.domain.model.NextWeekGames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val gameApiService: GameApiService
) : HomeRepository, BaseRepo() {

    override suspend fun getNextWeekGames(dates: String, platforms: String): Flow<DataState<List<NextWeekGames>>> =
        safeApiCall { gameApiService.getNextWeekGames(dates, platforms) }.map { resultDTODataState ->
            resultDTODataState.mapper { nextWeekGamesDTO -> nextWeekGamesDTO.results.map { it.mapToDomain() } }
        }


    override suspend fun getBestOfTheYear(dates: String, ordering: String): Flow<DataState<List<BestOfTheYear>>> =
        safeApiCall { gameApiService.getBestOfTheYear(dates, ordering) }.map { resultDTODataState ->
            resultDTODataState.mapper { bestOfTheYearDTO -> bestOfTheYearDTO.results.map { it.mapToDomain() } }
        }

}

