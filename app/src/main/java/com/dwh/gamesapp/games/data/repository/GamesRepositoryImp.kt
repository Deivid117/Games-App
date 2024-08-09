package com.dwh.gamesapp.games.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.a.data.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.a.data.database.dao.GameDao
import com.dwh.gamesapp.a.data.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.a.data.database.entities.toDatabase
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.model.favorite_game.toDomain
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.games.data.data_source.GamesDataSource
import com.dwh.gamesapp.games.data.repository.paging_source.GamesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GamesRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val gameDao: GameDao,
    private val favoriteGamesDao: FavoriteGamesDao,
    private val remoteDataSource: GamesDataSource
) : GamesRepository, BaseRepo() {

    /** TODO: este es el chido */
    override suspend fun getPaginatedGames(): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(pageSize = 100, prefetchDistance = 2),
            pagingSourceFactory = {
                GamesPagingSource(remoteDataSource)
            }
        ).flow
    }

    /** ADD FAVORITE GAME */
    override suspend fun addFavoriteGame(favoriteGame: FavoritGame) {
        favoriteGamesDao.insertFavoriteGame(favoriteGame.toDatabase())
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