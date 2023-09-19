package com.dwh.gamesapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.dwh.gamesapp.data.database.dao.UserDao
import com.dwh.gamesapp.data.database.entities.UserEntity
import com.dwh.gamesapp.data.database.entities.toDatabase
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.model.user.toDomain
import com.dwh.gamesapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.SQLException
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userDao: UserDao
): UserRepository {

    /** INSERT USER ROOM */
    override suspend fun insertUser(user: User) {
        try {
            userDao.insertUser(user.toDatabase())
        } catch (e: SQLiteConstraintException) {
            Log.e("InsertUser_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("InsertUser_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }
    /** SEARCH A USER BY EMAIL */
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

    /** USER LOGGED IN */
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

    /** SEARCH IF A USER IS LOGGED IN */
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

    /** GET USER DATA */
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

    /** VALIDATION IF A EMAIL ALREADY EXIST */
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
    }
}