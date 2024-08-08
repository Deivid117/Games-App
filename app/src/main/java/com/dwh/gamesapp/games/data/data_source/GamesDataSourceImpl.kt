package com.dwh.gamesapp.games.data.data_source

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.remote.api.BaseResponse
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.model.toDomain
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GamesDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : GamesDataSource, BaseResponse() {
    override suspend fun getGames(page: Int, pageSize: Int): Flow<Resource<List<Game>>> {
        return safeApiCall {
            apiService.getGames(page, pageSize)
        }.map { resource ->
            resource.map { model -> model.results.map { it.toDomain() } }
        }
    }
}