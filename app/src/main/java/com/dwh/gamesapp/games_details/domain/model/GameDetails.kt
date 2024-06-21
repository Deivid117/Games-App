package com.dwh.gamesapp.games_details.domain.model

import com.dwh.gamesapp.games_details.data.remote.model.response.GameDetailsDTO

data class GameDetails(
    var id : Int?,
    var nameOriginal : String?,
    var description : String?,
    var metacritic : Int?,
    var released : String?,
    var backgroundImage : String?,
    var backgroundImageAdditional : String?,
    var website : String?,
    var parentPlatforms : ArrayList<Platforms> = arrayListOf(),
    var platforms : ArrayList<Platforms> = arrayListOf(),
    var developers : ArrayList<Developers> = arrayListOf(),
    var genres : ArrayList<Genres> = arrayListOf(),
    var publishers : ArrayList<Publishers> = arrayListOf(),
    var esrbRatingResponse : EsrbRating? = EsrbRating(),
    var descriptionRaw : String?
)

fun GameDetailsDTO.toDomain() = GameDetails(
    id = id,
    nameOriginal,
    description,
    metacritic,
    released,
    backgroundImage,
    backgroundImageAdditional,
    website,
    parentPlatforms = parentPlatforms.map { it.toDomain() } as ArrayList<Platforms>,
    platforms = platforms.map { it.toDomain() } as ArrayList<Platforms>,
    developers = developers.map { it.toDomain() } as ArrayList<Developers>,
    genres = genres.map { it.toDomain() } as ArrayList<Genres>,
    publishers = publishers.map { it.toDomain() } as ArrayList<Publishers>,
    esrbRatingResponse = esrbRatingResponse?.toDomain(),
    descriptionRaw
)
