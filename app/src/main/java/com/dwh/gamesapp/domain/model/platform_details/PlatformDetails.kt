package com.dwh.gamesapp.domain.model.platform_details

import com.dwh.gamesapp.data.model.response.platform_details.PlatformDetailsResponse
import com.google.gson.annotations.SerializedName

data class PlatformDetails(
    var id              : Int,
    var name            : String,
    var slug            : String,
    var gamesCount      : Int,
    var imageBackground : String,
    var description     : String,
)

fun PlatformDetailsResponse.toDomain() = PlatformDetails(id, name, slug, gamesCount, imageBackground, description)
