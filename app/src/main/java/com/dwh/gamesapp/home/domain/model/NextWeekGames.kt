package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.a.data.model.response.games.NextWeekGamesDTO

data class NextWeekGames(
    override var name: String?,
    var released: String?,
    override var backgroundImage: String?,
    override var id: Int?
) : GameItem

fun NextWeekGamesDTO.toDomain() = NextWeekGames(name, released, backgroundImage, id)
