package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.home.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.home.data.remote.model.response.BestOfTheYearResultsDTO

data class BestOfTheYearResults(
    var results: ArrayList<BestOfTheYear> = arrayListOf(),
)

fun BestOfTheYearResultsDTO.toDomain() = BestOfTheYearResults(results.map { it.mapToDomain() } as ArrayList<BestOfTheYear>)
