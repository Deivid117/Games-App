package com.dwh.gamesapp.a.domain.repository

import com.dwh.gamesapp.a.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    // INSERT USER
    suspend fun insertUser(user: User)

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
    suspend fun updateUser(user: User)

}