package com.dwh.gamesapp.edit_profile.data.repository

import com.dwh.gamesapp.core.data.local.database.dao.UserDao
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.edit_profile.domain.repository.EditProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : EditProfileRepository, BaseRepo() {
    override suspend fun updateUser(
        name: String,
        password: String,
        profileAvatarId: Long,
        id: Long,
    ): Flow<DataState<Unit>> = safeRoomCall { userDao.updateUser(name, password, profileAvatarId, id) }
}