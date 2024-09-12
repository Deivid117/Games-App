package com.dwh.gamesapp.login.data.repository

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

    /* *//** SEARCH A USER BY EMAIL *//*
    override suspend fun findUserByEmail(email: String, password: String): User? {
        return try {
            userDao.findUserByUserEmail(email, password)
        } catch (e: SQLiteConstraintException) {
            Log.e("FindUser_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("FindUser_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }

    *//** USER LOGGED IN *//*
    override suspend fun updateUserLogged(isLogged: Boolean, userId: Long) {
        try {
            userDao.updateUserLogged(isLogged, userId)
        } catch (e: SQLiteConstraintException) {
            Log.e("UserLogged_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("UserLogged_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }

    *//** SEARCH IF A USER IS LOGGED IN *//*
    override suspend fun findUserLogged(): User? {
        return try {
            userDao.findUserLogged()
        } catch (e: SQLiteConstraintException) {
            Log.e("FindeUserLogged_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("FindeUserLogged_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }

    *//** GET USER DATA *//*
    override suspend fun getUserInfo(): Flow<User> {
        try {
            val user: Flow<UserEntity> =  userDao.getUserInfo()
            return user.map { it.toDomain() }
        } catch (e: SQLiteConstraintException) {
            Log.e("UserInfo_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("UserInfo_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }

    }

    *//** VALIDATION IF A EMAIL ALREADY EXIST *//*
    override suspend fun emailAlreadyExist(email: String): Flow<User?> {
        return try {
            userDao.emailAlreadyExist(email)
        } catch (e: SQLiteConstraintException) {
            Log.e("EmailExist_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("EmailExist_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }

    override suspend fun updateUser(user: User) {
        try {
            userDao.updateUser(user.toDatabase())
        } catch (e: SQLiteConstraintException) {
            Log.e("UpdateUser_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("UpdateUser_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }*/
}