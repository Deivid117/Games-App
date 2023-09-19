package com.dwh.gamesapp.data.database.dao

import androidx.room.*
import com.dwh.gamesapp.data.database.entities.UserEntity
import com.dwh.gamesapp.domain.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun findUserByUserEmail(email: String, password: String): User?

    @Query("SELECT * FROM user_table WHERE is_logged = 1 LIMIT 1")
    fun getUserInfo(): Flow<UserEntity>

    @Query("UPDATE user_table SET is_logged = :isLogged WHERE id = :userId")
    suspend fun updateUserLogged(isLogged: Boolean, userId: Long)

    @Query("SELECT * FROM user_table WHERE is_logged = 1")
    suspend fun findUserLogged(): User?

    @Query("SELECT * FROM user_table WHERE email =:email")
    fun emailAlreadyExist(email: String): Flow<User?>

    @Update
    suspend fun updateUser(userEntity: UserEntity)
}