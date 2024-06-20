package com.dwh.gamesapp.genres.domain.model

import com.dwh.gamesapp.genres.data.remote.model.response.GamesGenreDTO
import com.dwh.gamesapp.genres.data.remote.model.response.GenreDTO

data class Genre(
    var id: Int?,
    var name: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var games: ArrayList<GamesGenreDTO> = arrayListOf()
)

fun GenreDTO.toDomain() = Genre(id, name, gamesCount, imageBackground, games)