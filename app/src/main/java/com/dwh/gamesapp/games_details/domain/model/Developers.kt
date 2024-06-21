package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.DevelopersDTO

data class Developers(
    var id : Int?,
    var name : String?,
)

fun DevelopersDTO.toDomain() = Developers(id, name)