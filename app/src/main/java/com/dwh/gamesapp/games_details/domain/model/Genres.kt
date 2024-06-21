package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.GenresDTO

data class Genres(
    var id : Int?,
    var name : String?,
    var imageBackground : String?
)

fun GenresDTO.toDomain() = Genres(id, name, imageBackground)