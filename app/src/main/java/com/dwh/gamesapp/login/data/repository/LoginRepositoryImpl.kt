package com.dwh.gamesapp.login.data.repository

import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.data.local.database.dao.UserDao
import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.login.domain.repository.LoginRepository
import com.dwh.gamesapp.signup.data.local.database.mappers.mapToDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): LoginRepository, BaseRepo() {

    override suspend fun loginUser(email: String, password: String): Flow<DataState<User?>> =
        safeRoomCall { userDao.loginUser(email, password)?.mapToDomain() }

    override suspend fun loginUserWithFingerPrintToken(token: EncryptedData): Flow<DataState<User?>> =
        safeRoomCall { userDao.loginUserWithFingerPrintToken(token)?.mapToDomain() }

    override suspend fun updateUserFingerPrintToken(token: EncryptedData, userId: Long): Flow<DataState<Unit>> =
        safeRoomCall { userDao.updateUserFingerPrintToken(token, userId) }
}