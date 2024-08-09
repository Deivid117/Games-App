package com.dwh.gamesapp.genres.domain.model

data class Genre(
    var id: Int?,
    var name: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var games: ArrayList<GenreGame> = arrayListOf()
)