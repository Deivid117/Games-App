package com.dwh.gamesapp.platforms.domain.model

data class Platform(
    var id: Int?,
    var name: String?,
    var slug: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var games: ArrayList<PlatformGame> = arrayListOf()
)