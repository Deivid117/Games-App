package com.dwh.gamesapp.profile.data.repository

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.data.local.database.dao.UserDao
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.profile.domain.repository.ProfileRepository
import com.dwh.gamesapp.signup.data.local.database.mappers.mapToDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): ProfileRepository, BaseRepo() {
    override suspend fun getUserInformation(id: Long): Flow<DataState<User?>> =
        safeRoomCall { userDao.getUserInformation(id)?.mapToDomain() }
}