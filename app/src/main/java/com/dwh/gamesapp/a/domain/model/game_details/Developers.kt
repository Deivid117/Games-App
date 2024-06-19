package com.dwh.gamesapp.a.domain.model.game_details

import com.dwh.gamesapp.a.data.model.response.game_details.DevelopersResponse

data class Developers(
    var id : Int,
    var name : String,
)

fun DevelopersResponse.toDomain() = Developers(id, name)