package com.dwh.gamesapp.login.domain.repository

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun loginUser(email: String, password: String): Flow<DataState<User?>>
/*
    // SEARCH A USER BY EMAIL
    suspend fun findUserByEmail(email: String, password: String): User?

    // USER LOGGED IN
    suspend fun updateUserLogged(isLogged: Boolean, userId: Long)

    // SEARCH IF A USER IS LOGGED IN
    suspend fun findUserLogged(): User?

    // GET USER DATA
    suspend fun getUserInfo(): Flow<User>

    // VALIDATION IF A EMAIL ALREADY EXIST
    suspend fun emailAlreadyExist(email: String): Flow<User?>

    // UPDATE USER
    suspend fun updateUser(user: User)*/

}