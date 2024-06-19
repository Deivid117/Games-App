package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.home.data.remote.model.response.BestOfTheYearDTO

data class BestOfTheYear(
    override var name: String? = null,
    var released: String? = null,
    override var backgroundImage: String? = null,
    override var id: Int? = null
) : GameItem, GameListItem

fun BestOfTheYearDTO.toDomain() = BestOfTheYear(name, released, backgroundImage, id)

