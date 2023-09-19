package com.dwh.gamesapp.domain.model.genres

import com.dwh.gamesapp.data.model.response.genres.GenreGamesResponse
import com.google.gson.annotations.SerializedName

data class GenreGames(
    var id    : Int,
    var slug  : String,
    var name  : String,
    var added : Int
)

fun GenreGamesResponse.toDomain() = GenreGames(id, slug, name, added)
