package com.dwh.gamesapp.signup.data.repository

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.data.local.database.dao.UserDao
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.signup.data.local.database.mappers.mapToDatabase
import com.dwh.gamesapp.signup.data.local.database.mappers.mapToDomain
import com.dwh.gamesapp.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : SignupRepository, BaseRepo() {
    override suspend fun registerUser(user: User): Flow<DataState<Long>> =
        safeRoomCall { userDao.insertUser(user.mapToDatabase()) }

    override suspend fun userAlreadyExists(email: String, userName: String): Flow<DataState<User?>> =
        safeRoomCall { userDao.userAlreadyExists(email, userName)?.mapToDomain() }
}