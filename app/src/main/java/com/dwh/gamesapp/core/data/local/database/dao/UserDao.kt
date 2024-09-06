package com.dwh.gamesapp.core.data.local.database.dao

import androidx.room.*
import com.dwh.gamesapp.core.data.local.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): UserEntity?

    @Query("SELECT * FROM user_table WHERE email = :email OR name = :userName")
    suspend fun userAlreadyExists(email: String, userName: String): UserEntity?
}