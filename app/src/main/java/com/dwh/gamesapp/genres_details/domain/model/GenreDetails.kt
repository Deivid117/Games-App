package com.dwh.gamesapp.genres_details.domain.model

import com.dwh.gamesapp.genres_details.data.remote.model.response.GenreDetailsDTO

data class GenreDetails(
    var id: Int?,
    var name: String?,
    var slug: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var description: String?,
    var descriptionRaw: String?
)

fun GenreDetailsDTO.toDomain() = GenreDetails(id, name, slug, gamesCount, imageBackground, description, descriptionRaw)
