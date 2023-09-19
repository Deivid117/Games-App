package com.dwh.gamesapp.domain.model.game_details

import com.dwh.gamesapp.data.model.response.game_details.EsrbRatingResponse

data class EsrbRating(
    var id : Int = 0,
    var name : String = "",
)

fun EsrbRatingResponse.toDomain() = EsrbRating(id, name)