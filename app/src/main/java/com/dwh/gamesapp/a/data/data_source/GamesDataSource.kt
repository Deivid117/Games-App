package com.dwh.gamesapp.a.data.data_source

import androidx.paging.PagingData
import com.dwh.gamesapp.a.domain.model.game.GamesResults
import kotlinx.coroutines.flow.Flow

interface GamesDataSource {

    suspend fun getGamesPaged(): Flow<PagingData<GamesResults>>

}