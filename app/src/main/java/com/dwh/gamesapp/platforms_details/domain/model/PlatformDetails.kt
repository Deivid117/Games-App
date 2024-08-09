package com.dwh.gamesapp.platforms_details.domain.model

import com.dwh.gamesapp.platforms_details.data.remote.mode.response.PlatformDetailsDTO

data class PlatformDetails(
    var id              : Int? = null,
    var name            : String? = null,
    var slug            : String? = null,
    var gamesCount      : Int? = null,
    var imageBackground : String? = null,
    var description     : String? = null,
)

fun PlatformDetailsDTO.toDomain() = PlatformDetails(id, name, slug, gamesCount, imageBackground, description)
