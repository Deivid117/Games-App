package com.dwh.gamesapp.a.domain.model.game_details

import com.dwh.gamesapp.a.data.model.response.game_details.GenresResponse

data class Genres(
    var id : Int,
    var name : String,
    var imageBackground : String
)

fun GenresResponse.toDomain() = Genres(id, name, imageBackground)