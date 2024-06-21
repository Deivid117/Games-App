package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.a.data.model.response.game_details.GameDetailsResponse
import com.dwh.gamesapp.a.data.model.response.games.GamesBodyResponse
import com.dwh.gamesapp.a.data.model.response.games.NextWeekGamesResultsDTO
import com.dwh.gamesapp.genres_details.data.remote.model.response.GenreDetailsDTO
import com.dwh.gamesapp.genres.data.remote.model.response.GenresResultsDTO
import com.dwh.gamesapp.platforms_details.data.remote.mode.response.PlatformDetailsDTO
import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformsResultsDTO
import com.dwh.gamesapp.home.data.remote.model.response.BestOfTheYearResultsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /** WS GAMES */
    // FALTA
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<GamesBodyResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") id: Int): Response<GameDetailsResponse>

    // ARREGLADO
    @GET("games")
    suspend fun getNextWeekGames(
        @Query("dates") dates: String,
        @Query("platforms") platforms: String
    ): Response<NextWeekGamesResultsDTO>

    // ARREGLADO
    @GET("games")
    suspend fun getBestOfTheYear(
        @Query("dates") dates: String,
        @Query("ordering") ordering: String
    ): Response<BestOfTheYearResultsDTO>

    /** WS GENRES */
    // ARREGLADO
    @GET("genres")
    suspend fun getGenres(): Response<GenresResultsDTO>

    // ARREGLADO
    @GET("genres/{id}")
    suspend fun getGenreDetails(@Path("id") id: Int): Response<GenreDetailsDTO>

    /** WS PLATFORMS */
    // ARREGLADO
    @GET("platforms")
    suspend fun getPlatforms(): Response<PlatformsResultsDTO>

    @GET("platforms/{id}")
    suspend fun getPlatformDetails(@Path("id") id: Int): Response<PlatformDetailsDTO>

}