package com.dwh.gamesapp.genres.domain.model

import com.dwh.gamesapp.genres.data.remote.model.response.GenreDTO

data class Genre(
    var id: Int?,
    var name: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var games: ArrayList<GameGenre> = arrayListOf()
)

fun GenreDTO.toDomain() = Genre(id, name, gamesCount, imageBackground, games.map { it.toDomain() } as ArrayList<GameGenre>)