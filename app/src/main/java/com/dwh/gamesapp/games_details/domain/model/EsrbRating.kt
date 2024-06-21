package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.EsrbRatingDTO

data class EsrbRating(
    var id : Int? = null,
    var name : String? = null,
)

fun EsrbRatingDTO.toDomain() = EsrbRating(id, name)