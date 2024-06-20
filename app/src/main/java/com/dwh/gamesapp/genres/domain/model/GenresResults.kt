package com.dwh.gamesapp.genres.domain.model

import com.dwh.gamesapp.genres.data.remote.model.response.GenresResultsDTO

data class GenresResults(
    var results: ArrayList<Genre> = arrayListOf()
)

fun GenresResultsDTO.toDomain() = GenresResults(results = results.map { it.toDomain() } as ArrayList<Genre>)
