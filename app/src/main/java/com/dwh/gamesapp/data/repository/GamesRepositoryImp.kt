package com.dwh.gamesapp.data.repository

import android.util.Log
import com.dwh.gamesapp.data.api.ApiService
import com.dwh.gamesapp.data.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.data.database.dao.GameDao
import com.dwh.gamesapp.data.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.data.database.entities.GameEntity
import com.dwh.gamesapp.data.database.entities.toDatabase
import com.dwh.gamesapp.data.model.response.game_details.GameDetailsResponse
import com.dwh.gamesapp.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.domain.model.favorite_game.toDomain
import com.dwh.gamesapp.domain.model.game.GamesResults
import com.dwh.gamesapp.domain.model.game.NextWeekGamesResults
import com.dwh.gamesapp.domain.model.game.toDomain
import com.dwh.gamesapp.domain.model.game_details.GameDetails
import com.dwh.gamesapp.domain.model.game_details.toDomain
import com.dwh.gamesapp.domain.repository.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class GamesRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val gameDao: GameDao,
    private val favoriteGamesDao: FavoriteGamesDao
): GamesRepository {

    /** GET GAMES */
    override suspend fun getGamesFromApi(page: Int, pageSize: Int): Flow<List<GamesResults>> {
        val response = apiService.getGames(page, pageSize)
        if (response.isSuccessful) {
            return flowOf(
                apiService.getGames(page, pageSize)
                    .body()!!.results.map { it.toDomain() }).catch {
                Log.e("GamesFromApi", it.message ?: "ocurrió un error en la api") }
        } else {
            Log.e("GAMES_API_ERROR", "Ocurrió un error en la respuesta de la api")
            throw Exception("Ocurrió un error en la respuesta de la api")
        }
    }

    override suspend fun getGamesFromDatabase(): Flow<List<GamesResults>> {
        val response = gameDao.getAllGames()
        return flowOf(response.map { it.toDomain() }).catch {
            Log.e("GamesFromDatabase", it.message ?: "")
            emptyList<GamesResults>()
        }
    }

    override suspend fun getAllGames(page: Int, pageSize: Int): Flow<List<GamesResults>> {
        return flow {
            val gamesFromApi: Flow<List<GamesResults>> = try {
                getGamesFromApi(page, pageSize)
            } catch (e: Exception) {
                Log.e("AllGames", "API_ERROR ${e.message}")
                flowOf(emptyList())
            }
            if (gamesFromApi.single().isNotEmpty()) { // single() convierte el flow<list> en list
                Log.i("AllGames", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    //deleteAllGames()
                    Log.i("AllGames", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("AllGames", e.message ?: "Error desconocido")
                }
                val gamesEntity = gamesFromApi.single().map { it.toDatabase() }
                try {
                    insertGames(gamesEntity)
                    Log.i("AllGames", "ROOM_RESPONSE: Se insertaron nuevos datos")
                } catch (e: Exception) {
                    Log.e("AllGames", e.message ?: "Error desconocido")
                }
                emit(gamesFromApi.single())
            } else {
                Log.i("AllGames", "API_RESPONSE: No hay datos por parte de la api")
                val gamesFromDataBase: List<GamesResults> =
                    getGamesFromDatabase().firstOrNull() ?: emptyList()
                if (gamesFromDataBase.isNotEmpty()) {
                    emit(gamesFromDataBase)
                    Log.i("AllGames", "ROOM_RESPONSE: Obtuviste la información de la bd")
                } else {
                    Log.i("AllGames", "ROOM_RESPONSE: La base de datos está vacía")
                    throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
                }
                //emit(gamesFromDataBase) //Retorna la lista vacía o no
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNextWeekGames(dates: String): Flow<List<NextWeekGamesResults>> {
        return flowOf(apiService.getNextWeekGames(dates, "4,187,186,7,21").body()!!.results.map { it.toDomain() })
    }

    /** INSERT GAMES ROOM */
    override suspend fun insertGames(gameEntity: List<GameEntity>) {
        return gameDao.insertGame(gameEntity)
    }

    /** DELETE GAMES ROOM */
    override suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    /** GET GAME DETAILS */
    override suspend fun getGameDetailsFromApi(id: Int): Flow<GameDetails> {
        val response: Response<GameDetailsResponse> = apiService.getGameDetails(id)
        if (response.isSuccessful) {
            return flowOf(
                apiService.getGameDetails(id).body()!!).map { it.toDomain() } .catch {
                Log.e("GameDetailsFromApi", it.message ?: "ocurrió un error en la api") }
        } else {
            Log.e("GAMES_DETAILS_API_ERROR", "Ocurrió un error en la respuesta de la api")
            throw Exception("Ocurrió un error en la respuesta de la api")
        }
    }

    override suspend fun getGameDetails(id: Int): Flow<GameDetails> {
        return flow {
            val gamesDetailsFromApi: Flow<GameDetails?> = try {
                getGameDetailsFromApi(id)
            } catch (e: Exception) {
                Log.e("GameDetails", "API_ERROR ${e.message}")
                flowOf(null)
            }
            if (gamesDetailsFromApi.single() != null) {
                Log.i("GameDetails", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    //deleteAllGames()
                    Log.i("GameDetails", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("GameDetails", e.message ?: "Error desconocido")
                }
                emit(gamesDetailsFromApi.single()!!)
            } else {
                Log.i("GameDetails", "API_RESPONSE: No hay datos por parte de la api")
                throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
                //emit(gamesDetailsFromApi.single())
            }
        }.flowOn(Dispatchers.IO)
    }

    /** ADD FAVORITE GAME */
    override suspend fun addFavoriteGame(favoriteGame: FavoritGame) {
        favoriteGamesDao.insertFavoriteGame(favoriteGame.toDatabase())
    }

    override suspend fun getAllFavoritesGames(): Flow<List<FavoritGame>> {
        return flow {
            val favoritGamesFromDataBase: List<FavoriteGameEntity> = favoriteGamesDao.getAllFavoriteGames()
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