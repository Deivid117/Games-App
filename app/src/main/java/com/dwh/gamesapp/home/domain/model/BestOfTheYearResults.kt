package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.home.data.remote.model.response.BestOfTheYearResultsDTO

data class BestOfTheYearResults(
    var results: ArrayList<BestOfTheYear> = arrayListOf(),
)

fun BestOfTheYearResultsDTO.toDomain() = BestOfTheYearResults(results.map { it.toDomain() } as ArrayList<BestOfTheYear>)
