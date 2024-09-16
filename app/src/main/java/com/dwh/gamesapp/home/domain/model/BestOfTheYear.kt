package com.dwh.gamesapp.home.domain.model

data class BestOfTheYear(
    override var name: String? = null,
    var released: String? = null,
    override var backgroundImage: String? = null,
    override var id: Int? = null
) : GameItem, GameListItem
