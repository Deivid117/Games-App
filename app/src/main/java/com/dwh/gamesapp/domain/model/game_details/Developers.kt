package com.dwh.gamesapp.domain.model.game_details

import com.dwh.gamesapp.data.model.response.game_details.DevelopersResponse
import com.google.gson.annotations.SerializedName

data class Developers(
    var id : Int,
    var name : String,
)

fun DevelopersResponse.toDomain() = Developers(id, name)