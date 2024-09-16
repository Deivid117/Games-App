package com.dwh.gamesapp.favorite_games.data.repository

import com.dwh.gamesapp.favorite_games.data.local.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.favorite_games.data.local.database.mappers.mapToDatabase
import com.dwh.gamesapp.favorite_games.data.local.database.mappers.mapToDomain
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.favorite_games.domain.repository.FavoriteGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteGamesRepositoryImpl @Inject constructor(
    private val favoriteGamesDao: FavoriteGamesDao
) : FavoriteGamesRepository, BaseRepo(){
    override suspend fun insertFavoriteGame(favoriteGame: FavoriteGame): Flow<DataState<Unit>> =
        safeRoomCall { favoriteGamesDao.insertFavoriteGame(favoriteGame.mapToDatabase()) }


    override suspend fun getAllFavoriteGames(): Flow<DataState<List<FavoriteGame>>> =
        safeRoomCall { favoriteGamesDao.getAllFavoriteGames() }.map { favoriteGameEntityDataState ->
            favoriteGameEntityDataState.mapper { favoriteGameEntity -> favoriteGameEntity.map { it.mapToDomain() } } }

    override suspend fun isMyFavoriteGame(gameId: Int): Flow<DataState<Boolean>> =
        safeRoomCall { favoriteGamesDao.isMyFavoriteGame(gameId)?.mapToDatabase() != null }

    override suspend fun deleteFavoriteGame(gameId: Int): Flow<DataState<Unit>> =
        safeRoomCall { favoriteGamesDao.deleteFavoriteGame(gameId) }
}