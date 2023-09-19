package com.dwh.gamesapp.domain.model.game_details

import com.dwh.gamesapp.data.model.response.game_details.PlatformsResponse

data class Platfforms(
    var platform : Plattform = Plattform(),
)

fun PlatformsResponse.toDomain() = Platfforms(platform = platform.toDomain())