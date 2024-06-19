package com.dwh.gamesapp.a.domain.model.game_details

import com.dwh.gamesapp.a.data.model.response.game_details.PublishersResponse

data class Publishers(
    var id : Int,
    var name : String,
)

fun PublishersResponse.toDomain() = Publishers(id, name)