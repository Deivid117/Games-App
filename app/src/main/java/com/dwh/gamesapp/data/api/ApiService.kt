package com.dwh.gamesapp.data.api

import com.dwh.gamesapp.data.model.response.platform_details.PlatformDetailsResponse
import com.dwh.gamesapp.data.model.response.games.GamesBodyResponse
import com.dwh.gamesapp.data.model.response.game_details.GameDetailsResponse
import com.dwh.gamesapp.data.model.response.genre_details.GenreDetailsResponse
import com.dwh.gamesapp.data.model.response.genres.GenresBodyResponse
import com.dwh.gamesapp.data.model.response.plattforms.PlatformsBodyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // GAMES
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<GamesBodyResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") id: Int): Response<GameDetailsResponse>

    // GENRES
    @GET("genres")
    suspend fun getGenres(): Response<GenresBodyResponse>

    @GET("genres/{id}")
    suspend fun getGenreDetails(@Path("id") id: Int): Response<GenreDetailsResponse>

    // PLATTFORMS
    @GET("platforms")
    suspend fun getPlatforms(): Response<PlatformsBodyResponse>

    @GET("platforms/{id}")
    suspend fun getPlatformsDetails(@Path("id") id: Int): Response<PlatformDetailsResponse>

}