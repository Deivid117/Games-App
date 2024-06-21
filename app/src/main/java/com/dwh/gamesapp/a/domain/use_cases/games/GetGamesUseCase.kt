package com.dwh.gamesapp.a.domain.use_cases.games

import androidx.paging.PagingData
import com.dwh.gamesapp.a.data.data_source.GamesDataSource
import com.dwh.gamesapp.a.domain.model.game.GamesResults
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gamesDataSource: GamesDataSource
) {
    suspend operator fun invoke(): Flow<PagingData<GamesResults>> {
        return gamesDataSource.getGamesPaged()
    }
    /*suspend fun execute(page: Int, pageSize: Int): Flow<PagingData<GamesResults>> {
        //Implementación
    }*/
}