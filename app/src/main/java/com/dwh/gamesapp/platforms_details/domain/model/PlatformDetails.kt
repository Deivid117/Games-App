package com.dwh.gamesapp.platforms_details.domain.model

import com.dwh.gamesapp.platforms_details.data.remote.mode.response.PlatformDetailsDTO

data class PlatformDetails(
    var id              : Int?,
    var name            : String?,
    var slug            : String?,
    var gamesCount      : Int?,
    var imageBackground : String?,
    var description     : String?,
)

fun PlatformDetailsDTO.toDomain() = PlatformDetails(id, name, slug, gamesCount, imageBackground, description)
