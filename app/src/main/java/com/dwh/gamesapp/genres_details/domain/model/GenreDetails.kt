package com.dwh.gamesapp.genres_details.domain.model

data class GenreDetails(
    var id: Int? = null,
    var name: String? = null,
    var slug: String? = null,
    var gamesCount: Int? = null,
    var imageBackground: String? = null,
    var description: String? = null,
    var descriptionRaw: String? = null
)