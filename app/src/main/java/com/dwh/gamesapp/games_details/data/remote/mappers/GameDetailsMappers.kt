package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.GameDetailsDTO
import com.dwh.gamesapp.games_details.domain.model.Developers
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.games_details.domain.model.Genres
import com.dwh.gamesapp.games_details.domain.model.Platforms
import com.dwh.gamesapp.games_details.domain.model.Publishers

fun GameDetailsDTO.mapToDomain() = GameDetails(
    id = id,
    nameOriginal = nameOriginal,
    description = description,
    metacritic = metacritic,
    released = released,
    backgroundImage = backgroundImage,
    backgroundImageAdditional = backgroundImageAdditional,
    website = website,
    parentPlatforms = parentPlatforms.map { it.mapToDomain() } as ArrayList<Platforms>,
    platforms = platforms.map { it.mapToDomain() } as ArrayList<Platforms>,
    developers = developers.map { it.mapToDomain() } as ArrayList<Developers>,
    genres = genres.map { it.mapToDomain() } as ArrayList<Genres>,
    publishers = publishers.map { it.mapToDomain() } as ArrayList<Publishers>,
    esrbRatingResponse = esrbRatingResponse?.mapToDomain(),
    descriptionRaw = descriptionRaw
)
