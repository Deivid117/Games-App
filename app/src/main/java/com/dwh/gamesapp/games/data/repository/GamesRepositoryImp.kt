package com.dwh.gamesapp.games.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dwh.gamesapp.core.data.local.database.GameDatabase
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games.data.local.database.mappers.mapToDomain
import com.dwh.gamesapp.games.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.games.data.repository.remote_mediator.GameRemoteMediator
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GamesRepositoryImp @Inject constructor(
    gameDatabase: GameDatabase,
    private val gameRemoteMediator: GameRemoteMediator,
    private val gameApiService: GameApiService
) : GamesRepository, BaseRepo() {

    private val gameDao = gameDatabase.gameDao()

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPaginatedGames(): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                // TODO ver si dejar estos atributos
                prefetchDistance = 10,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = gameDao.getGames().map { it.mapToDomain() }.asPagingSourceFactory(),
            remoteMediator = gameRemoteMediator
        ).flow
    }

    override suspend fun searchGames(search: String): Flow<DataState<List<Game>>> =
        safeApiCall { gameApiService.getGames(
            page = 1,
            pageSize = 100,
            search = search
        ) }.map { resultDTODataState ->
            resultDTODataState.mapper { gameDTO -> gameDTO.results.map { it.mapToDomain() } }
        }
}