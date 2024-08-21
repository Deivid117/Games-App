package com.dwh.gamesapp.games.data.repository.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dwh.gamesapp.core.data.local.database.GameDatabase
import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.games.data.local.database.dao.GameDao
import com.dwh.gamesapp.games.data.local.database.dao.RemoteKeyDao
import com.dwh.gamesapp.games.data.local.database.entities.RemoteKeyEntity
import com.dwh.gamesapp.games.data.remote.mappers.mapToEntity
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.presentation.utils.CustomException
import com.dwh.gamesapp.games.presentation.utils.ErrorMessage
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val gameDatabase: GameDatabase,
    private val gameApiService: GameApiService,
) : RemoteMediator<Int, Game>() {

    private val gameDao: GameDao = gameDatabase.gameDao()
    private val remoteKeyDao: RemoteKeyDao = gameDatabase.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey = remoteKeyDao.getRemoteKeyByGameId("game_id")
            ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.last_updated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Game>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.getRemoteKeyByGameId("game_id")
                        ?: return MediatorResult.Success(true)
                    remoteKey.next_page ?: return MediatorResult.Success(true)
                }
            }

            val response = gameApiService.getGames(
                page = page,
                pageSize = state.config.pageSize
            )

            if (!response.isSuccessful) {
                return MediatorResult.Error(
                    CustomException(
                        ErrorMessage(
                            errorMessage = "Error de Conexión",
                            errorDescription = "Ocurrió un error al solicitar los datos, por favor inténtelo más tarde"
                        )
                    )
                )
            }

            val games = response.body()?.results?.map { it.mapToEntity() } ?: emptyList()

            if (games.isEmpty()) {
                return MediatorResult.Error(
                    CustomException(
                        ErrorMessage(
                            errorMessage = "Sin Datos Disponibles",
                            errorDescription = "No pudimos encontrar la información solicitada"
                        )
                    )
                )
            }

            gameDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    gameDao.deleteGames()
                    remoteKeyDao.clearRemoteKeys()
                }

                val nextPage = if (games.isEmpty()) null else page + 1

                remoteKeyDao.insertRemoteKey(
                    RemoteKeyEntity(
                        id = "game_id",
                        next_page = nextPage,
                        last_updated = System.currentTimeMillis()
                    )
                )

                gameDao.insertGames(games)
            }

            MediatorResult.Success(endOfPaginationReached = games.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(
                CustomException(
                    errorMessage = ErrorMessage(
                        errorMessage = "Sin Conexión a Internet",
                        errorDescription = "Por favor, verifica tu conexión a internet y vuelve a intentarlo"
                    )
                )
            )
        } catch (e: HttpException) {
            MediatorResult.Error(
                CustomException(
                    ErrorMessage(
                        errorMessage = "Error del Servidor", errorDescription = e.message
                            ?: "Estamos experimentando problemas con el servidor. Por favor, inténtalo más tarde"
                    )
                )
            )
        } catch (e: Exception) {
            MediatorResult.Error(
                CustomException(
                    ErrorMessage(
                        errorMessage = "Algo salió mal", errorDescription = e.message ?: "Error desconocido"
                    )
                )
            )
        }
    }
}