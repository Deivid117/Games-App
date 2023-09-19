package com.dwh.gamesapp.domain.use_cases.games

import androidx.paging.PagingData
import com.dwh.gamesapp.data.data_source.GamesDataSource
import com.dwh.gamesapp.domain.model.game.GamesResults
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/** Crea casos de uso dependiendo de todas las acciones que vayas a realizar */
/** Puedes usar cualquiera de las 2 funciones, cada una es una manera de
 * llamar a la fun del caso de uso, ambas válidas */
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