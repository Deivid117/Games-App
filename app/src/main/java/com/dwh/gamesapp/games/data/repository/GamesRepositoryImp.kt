package com.dwh.gamesapp.games.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dwh.gamesapp.a.data.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.model.favorite_game.toDomain
import com.dwh.gamesapp.core.data.local.database.GameDatabase
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.games.data.local.database.mappers.mapToDomain
import com.dwh.gamesapp.games.data.repository.remote_mediator.GameRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GamesRepositoryImp @Inject constructor(
    gameDatabase: GameDatabase,
    private val gameRemoteMediator: GameRemoteMediator
) : GamesRepository, BaseRepo() {

    private val gameDao = gameDatabase.gameDao()
    private val favoriteGamesDao = gameDatabase.favoriteGameDao()

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

    // TODO arreglar todos estos métodos
    /** ADD FAVORITE GAME */
    override suspend fun addFavoriteGame(favoriteGame: FavoritGame) {
    }

    override suspend fun getAllFavoritesGames(): Flow<List<FavoritGame>> {
        return flow {
            val favoritGamesFromDataBase: List<FavoriteGameEntity> =
                favoriteGamesDao.getAllFavoriteGames()
            if (favoritGamesFromDataBase.isNotEmpty()) {
                emit(favoritGamesFromDataBase.map { it.toDomain() })
                Log.i("AllGames", "ROOM_RESPONSE: Obtuviste la información de la bd")
            } else {
                Log.i("AllGames", "ROOM_RESPONSE: La base de datos está vacía")
                throw Exception("ERROR, no hay datos por parte de la base de datos")
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun isMyFavoriteGame(id: Int): Boolean {
        val game = favoriteGamesDao.isMyFavoriteGame(id)
        return game != null
    }

    override suspend fun removeFromFavoriteGames(id: Int) {
        favoriteGamesDao.removeFromFavoriteGames(id)
    }
}