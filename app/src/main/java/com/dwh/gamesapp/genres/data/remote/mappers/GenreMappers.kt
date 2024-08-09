package com.dwh.gamesapp.genres.data.remote.mappers

import com.dwh.gamesapp.genres.data.remote.model.response.GenreDTO
import com.dwh.gamesapp.genres.data.remote.model.response.GenreGameDTO
import com.dwh.gamesapp.genres.domain.model.GenreGame
import com.dwh.gamesapp.genres.domain.model.Genre

fun GenreDTO.mapToDomain() = Genre(
    id = id,
    name = name,
    gamesCount = gamesCount,
    imageBackground = imageBackground,
    games = games.map { it.mapToDomain() } as ArrayList<GenreGame>
)
fun GenreGameDTO.mapToDomain() = GenreGame(
    id = id,
    slug = slug,
    name = name,
    added = added
)
