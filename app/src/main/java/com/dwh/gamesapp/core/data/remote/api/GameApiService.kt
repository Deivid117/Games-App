package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.games_details.data.remote.model.response.GameDetailsDTO
import com.dwh.gamesapp.games.data.remote.model.GamesResultDTO
import com.dwh.gamesapp.home.data.remote.model.response.NextWeekGamesResultsDTO
import com.dwh.gamesapp.genres_details.data.remote.model.response.GenreDetailsDTO
import com.dwh.gamesapp.genres.data.remote.model.response.GenreResultsDTO
import com.dwh.gamesapp.platforms_details.data.remote.model.response.PlatformDetailsDTO
import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformResultsDTO
import com.dwh.gamesapp.home.data.remote.model.response.BestOfTheYearResultsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApiService {

    /** WS GAMES */
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("search") search: String
    ): Response<GamesResultDTO>

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") id: Int): Response<GameDetailsDTO>

    @GET("games")
    suspend fun getNextWeekGames(
        @Query("dates") dates: String,
        @Query("platforms") platforms: String
    ): Response<NextWeekGamesResultsDTO>

    @GET("games")
    suspend fun getBestOfTheYear(
        @Query("dates") dates: String,
        @Query("ordering") ordering: String
    ): Response<BestOfTheYearResultsDTO>

    /** WS GENRES */
    @GET("genres")
    suspend fun getGenres(): Response<GenreResultsDTO>

    @GET("genres/{id}")
    suspend fun getGenreDetails(@Path("id") id: Int): Response<GenreDetailsDTO>

    /** WS PLATFORMS */
    @GET("platforms")
    suspend fun getPlatforms(): Response<PlatformResultsDTO>

    @GET("platforms/{id}")
    suspend fun getPlatformDetails(@Path("id") id: Int): Response<PlatformDetailsDTO>
}