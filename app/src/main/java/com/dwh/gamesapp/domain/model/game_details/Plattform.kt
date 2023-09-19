package com.dwh.gamesapp.domain.model.game_details

import com.dwh.gamesapp.data.model.response.game_details.PlatformResponse
import com.google.gson.annotations.SerializedName

data class Plattform(
    var id : Int = 0,
   var name : String = "",
)

fun PlatformResponse.toDomain() = Plattform(id, name)