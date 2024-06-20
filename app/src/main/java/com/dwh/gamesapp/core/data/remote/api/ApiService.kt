package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.a.data.model.response.game_details.GameDetailsResponse
import com.dwh.gamesapp.a.data.model.response.games.GamesBodyResponse
import com.dwh.gamesapp.a.data.model.response.games.NextWeekGamesResultsDTO
import com.dwh.gamesapp.a.data.model.response.genre_details.GenreDetailsResponse
import com.dwh.gamesapp.genres.data.remote.model.response.GenresResultsDTO
import com.dwh.gamesapp.a.data.model.response.platform_details.PlatformDetailsResponse
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
    // FALTA
    @GET("genres")
    suspend fun getGenres(): Response<GenresResultsDTO>

    @GET("genres/{id}")
    suspend fun getGenreDetails(@Path("id") id: Int): Response<GenreDetailsResponse>

    /** WS PLATFORMS */
    // ARREGLADO
    @GET("platforms")
    suspend fun getPlatforms(): Response<PlatformsResultsDTO>

    @GET("platforms/{id}")
    suspend fun getPlatformsDetails(@Path("id") id: Int): Response<PlatformDetailsResponse>

}