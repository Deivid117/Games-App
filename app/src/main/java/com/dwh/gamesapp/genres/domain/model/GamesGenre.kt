package com.dwh.gamesapp.genres.domain.model

import com.dwh.gamesapp.genres.data.remote.model.response.GamesGenreDTO

data class GamesGenre(
    var id    : Int?,
    var slug  : String?,
    var name  : String?,
    var added : Int?
)

fun GamesGenreDTO.toDomain() = GamesGenre(id, slug, name, added)
