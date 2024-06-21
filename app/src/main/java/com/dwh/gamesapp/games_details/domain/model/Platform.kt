package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.PlatformDTO

data class Platform(
    var id : Int? = null,
   var name : String? = null,
)

fun PlatformDTO.toDomain() = Platform(id, name)