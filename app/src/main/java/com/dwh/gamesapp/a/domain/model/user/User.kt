package com.dwh.gamesapp.a.domain.model.user

import com.dwh.gamesapp.a.data.database.entities.UserEntity

data class User(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String? = null,
    val isLogged: Boolean? = false,
    val image_id: Long = 0
)

fun UserEntity.toDomain() = User(id = id, name = name, email = email, password = password, isLogged = isLogged, image_id = imageId)

