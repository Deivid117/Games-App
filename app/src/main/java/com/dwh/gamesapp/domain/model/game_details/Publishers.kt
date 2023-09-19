package com.dwh.gamesapp.domain.model.game_details

import com.dwh.gamesapp.data.model.response.game_details.PublishersResponse
import com.google.gson.annotations.SerializedName

data class Publishers(
    var id : Int,
    var name : String,
)

fun PublishersResponse.toDomain() = Publishers(id, name)