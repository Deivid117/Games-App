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

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserInformation(id: Long): UserEntity?

    @Query("""
        UPDATE user_table 
        SET name = CASE WHEN :name != '' THEN :name ELSE name END,
            password = CASE WHEN :password != '' THEN :password ELSE password END,
            profile_avatar_id = CASE WHEN :profileAvatarId != '' THEN :profileAvatarId ELSE profile_avatar_id END
        WHERE id = :id
    """)
    suspend fun updateUser(name: String, password: String, profileAvatarId: Long, id: Long)
}