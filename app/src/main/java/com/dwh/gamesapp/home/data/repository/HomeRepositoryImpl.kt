package com.dwh.gamesapp.home.data.repository

import com.dwh.gamesapp.a.data.api.ApiService
import com.dwh.gamesapp.home.domain.model.BestOfTheYearResults
import com.dwh.gamesapp.home.domain.model.toDomain
import com.dwh.gamesapp.home.domain.repository.HomeRepository
import com.dwh.gamesapp.core.data.remote.api.BaseResponse
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.home.domain.model.NextWeekGamesResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl@Inject constructor(
    private val apiService: ApiService,
): HomeRepository, BaseResponse() {

    override suspend fun getNextWeekGames(dates: String, platforms: String): Flow<Resource<NextWeekGamesResults>> {
        return safeApiCall {
            apiService.getNextWeekGames(dates, platforms)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
    }

    override suspend fun getBestOfTheYear(dates: String, ordering: String): Flow<Resource<BestOfTheYearResults>> {
        return safeApiCall {
            apiService.getBestOfTheYear(dates, ordering)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
    }
}

