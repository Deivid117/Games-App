package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.home.data.remote.model.response.NextWeekGamesDTO

data class NextWeekGames(
    override var name: String?,
    var released: String?,
    override var backgroundImage: String?,
    override var id: Int?
) : GameItem

fun NextWeekGamesDTO.toDomain() = NextWeekGames(name, released, backgroundImage, id)
