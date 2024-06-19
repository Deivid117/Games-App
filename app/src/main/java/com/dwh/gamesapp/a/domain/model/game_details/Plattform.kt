package com.dwh.gamesapp.a.domain.model.game_details

import com.dwh.gamesapp.a.data.model.response.game_details.PlatformResponse

data class Plattform(
    var id : Int = 0,
   var name : String = "",
)

fun PlatformResponse.toDomain() = Plattform(id, name)