package com.dwh.gamesapp.home.domain.model

data class NextWeekGames(
    override var name: String?,
    var released: String?,
    override var backgroundImage: String?,
    override var id: Int?
) : GameItem