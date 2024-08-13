package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.games_details.data.remote.model.response.GameDetailsDTO
import com.dwh.gamesapp.games.data.model.GamesResultDTO
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

interface ApiService {

    /** WS GAMES */
    // FALTA
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<GamesResultDTO>

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") id: Int): Response<GameDetailsDTO>

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
    // ARREGLADO V2.0
    @GET("genres")
    suspend fun getGenres(): Response<GenreResultsDTO>

    // ARREGLADO V2.0
    @GET("genres/{id}")
    suspend fun getGenreDetails(@Path("id") id: Int): Response<GenreDetailsDTO>

    /** WS PLATFORMS */
    // ARREGLADO v2.0
    @GET("platforms")
    suspend fun getPlatforms(): Response<PlatformResultsDTO>

    // ARREGLADO V2.0
    @GET("platforms/{id}")
    suspend fun getPlatformDetails(@Path("id") id: Int): Response<PlatformDetailsDTO>

}