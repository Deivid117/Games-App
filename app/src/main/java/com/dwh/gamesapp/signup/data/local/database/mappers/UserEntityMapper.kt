package com.dwh.gamesapp.signup.data.local.database.mappers

import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.core.data.local.database.entities.UserEntity

fun UserEntity.mapToDomain() = User(
    id = id,
    name = name,
    email = email,
    password = password,
    profileAvatarId
)

fun User.mapToDatabase() = UserEntity(
    id = id,
    name = name,
    email = email,
    password = password,
    profileAvatarId
)