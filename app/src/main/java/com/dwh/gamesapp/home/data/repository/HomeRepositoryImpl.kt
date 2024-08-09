package com.dwh.gamesapp.home.data.repository

import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.home.domain.model.BestOfTheYearResults
import com.dwh.gamesapp.home.domain.model.toDomain
import com.dwh.gamesapp.home.domain.repository.HomeRepository
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.home.domain.model.NextWeekGamesResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl@Inject constructor(
    private val apiService: ApiService,
): HomeRepository, BaseRepo() {

    override suspend fun getNextWeekGames(dates: String, platforms: String): Flow<Resource<NextWeekGamesResults>> {
        return safeApiCall2 {
            apiService.getNextWeekGames(dates, platforms)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
    }

    override suspend fun getBestOfTheYear(dates: String, ordering: String): Flow<Resource<BestOfTheYearResults>> {
        return safeApiCall2 {
            apiService.getBestOfTheYear(dates, ordering)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
    }
}

