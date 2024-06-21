package com.dwh.gamesapp.genres.domain.model

import com.dwh.gamesapp.genres.data.remote.model.response.GameGenreDTO

data class GameGenre(
    var id    : Int?,
    var slug  : String?,
    var name  : String?,
    var added : Int?
)

fun GameGenreDTO.toDomain() = GameGenre(id, slug, name, added)
