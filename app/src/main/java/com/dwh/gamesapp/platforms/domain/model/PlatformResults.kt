package com.dwh.gamesapp.platforms.domain.model

import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformsResultsDTO

data class PlatformResults(
    var results: ArrayList<Platform> = arrayListOf()
)

fun PlatformsResultsDTO.toDomain() = PlatformResults(results = results.map { it.toDomain() } as ArrayList<Platform>)