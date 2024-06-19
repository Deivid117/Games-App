package com.dwh.gamesapp.a.domain.model.genres

import com.dwh.gamesapp.a.data.model.response.genres.GenreResultsResponse

data class GenresResults(
    var id : Int,
    var name : String,
    var gamesCount      : Int,
    var imageBackground : String,
    var games           : ArrayList<GenreGames> = arrayListOf()

)

fun GenreResultsResponse.toDomain() = GenresResults(id, name, gamesCount, imageBackground,
    games.map { it.toDomain() } as ArrayList<GenreGames>)
