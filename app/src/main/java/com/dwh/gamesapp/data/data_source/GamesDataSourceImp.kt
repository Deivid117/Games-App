package com.dwh.gamesapp.data.data_source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dwh.gamesapp.data.repository.paging.GamesPagingSource
import com.dwh.gamesapp.domain.model.game.GamesResults
import com.dwh.gamesapp.domain.repository.GamesRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GamesDataSourceImp @Inject constructor(
    private val gamesRepository: GamesRepository
) : GamesDataSource {

    override suspend fun getGamesPaged(): Flow<PagingData<GamesResults>> {
        return Pager(
            config = PagingConfig(pageSize = 100),
            pagingSourceFactory = {
                GamesPagingSource(gamesRepository)
            }
        ).flow
    }
}