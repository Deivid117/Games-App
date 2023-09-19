package com.dwh.gamesapp.domain.model.user

import com.dwh.gamesapp.data.database.entities.UserEntity

data class User(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String? = null,
    val isLogged: Boolean? = false
)

fun UserEntity.toDomain() = User(id = id, name = name, email = email, password = password, isLogged = isLogged)

