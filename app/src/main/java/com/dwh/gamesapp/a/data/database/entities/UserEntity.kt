package com.dwh.gamesapp.a.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwh.gamesapp.a.domain.model.user.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "password_confirmation") val passwordConfirmation: String? = null,
    @ColumnInfo(name = "is_logged") val isLogged: Boolean?,
    @ColumnInfo(name = "image_id") val imageId: Long = 0L,
)

fun User.toDatabase() = UserEntity(id = id, name = name, email = email, password = password, isLogged = isLogged, imageId = image_id)