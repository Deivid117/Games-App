package com.dwh.gamesapp.a.domain.model.genres

import com.dwh.gamesapp.a.data.model.response.genres.GenreGamesResponse

data class GenreGames(
    var id    : Int,
    var slug  : String,
    var name  : String,
    var added : Int
)

fun GenreGamesResponse.toDomain() = GenreGames(id, slug, name, added)
