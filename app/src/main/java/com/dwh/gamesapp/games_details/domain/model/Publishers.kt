package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.PublishersDTO

data class Publishers(
    var id : Int?,
    var name : String?,
)

fun PublishersDTO.toDomain() = Publishers(id, name)