package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.PlatformsDTO

data class Platforms(
    var platform : Platform? = Platform(),
)

fun PlatformsDTO.toDomain() = Platforms(platform = platform.toDomain())