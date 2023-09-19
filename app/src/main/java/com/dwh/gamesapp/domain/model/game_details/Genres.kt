package com.dwh.gamesapp.domain.model.game_details

import com.dwh.gamesapp.data.model.response.game_details.GenresResponse
import com.google.gson.annotations.SerializedName

data class Genres(
    var id : Int,
    var name : String,
    var imageBackground : String
)

fun GenresResponse.toDomain() = Genres(id, name, imageBackground)