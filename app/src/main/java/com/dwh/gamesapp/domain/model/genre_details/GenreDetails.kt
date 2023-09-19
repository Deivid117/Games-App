package com.dwh.gamesapp.domain.model.genre_details

import com.dwh.gamesapp.data.model.response.genre_details.GenreDetailsResponse
import com.google.gson.annotations.SerializedName

data class GenreDetails(
    var id              : Int,
    var name            : String,
    var slug            : String,
    var gamesCount      : Int,
    var imageBackground : String,
    var description     : String
)

fun GenreDetailsResponse.toDomain() = GenreDetails(id, name, slug, gamesCount, imageBackground, description)
