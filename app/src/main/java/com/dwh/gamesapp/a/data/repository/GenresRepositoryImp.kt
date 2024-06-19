package com.dwh.gamesapp.a.data.repository

import android.util.Log
import com.dwh.gamesapp.a.data.api.ApiService
import com.dwh.gamesapp.a.data.model.response.genres.GenresBodyResponse
import com.dwh.gamesapp.a.domain.model.genre_details.GenreDetails
import com.dwh.gamesapp.a.domain.model.genre_details.toDomain
import com.dwh.gamesapp.a.domain.model.genres.GenresResults
import com.dwh.gamesapp.a.domain.model.genres.toDomain
import com.dwh.gamesapp.a.domain.repository.GenresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class GenresRepositoryImp @Inject constructor(
    private val apiService: ApiService
): GenresRepository {

    /** GET GENRES */
    override suspend fun getGenresFromApi(): Flow<List<GenresResults>> {
        val response: Response<GenresBodyResponse> = apiService.getGenres()
        if (response.isSuccessful) {
            return flowOf(
                apiService.getGenres().body()!!.results.map { it.toDomain() }).catch {
                Log.e("GenresFromApi", it.message ?: "ocurrió un error en la api")
            }
        } else {
            Log.e("GENRES_API_ERROR", "Ocurrió un error en la respuesta de la api")
            throw Exception("Ocurrió un error en la respuesta de la api")
        }
    }

    override suspend fun getAllGenres(): Flow<List<GenresResults>> {
        return flow {
            val genresFromApi: Flow<List<GenresResults>> = try {
                getGenresFromApi()
            } catch (e: Exception) {
                Log.e("AllGenres", "API_ERROR ${e.message}")
                flowOf(emptyList<GenresResults>())
            }
            if (genresFromApi.single().isNotEmpty()) { // single() convierte el flow<list> en list
                Log.i("AllGenres", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    Log.i("AllGenres", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("AllGenres", e.message ?: "Error desconocido")
                }
                try {

                } catch (e: Exception) {
                    Log.e("AllGenres", e.message ?: "Error desconocido")
                }
                emit(genresFromApi.single())
            } else {
                Log.i("AllGenres", "API_RESPONSE: No hay datos por parte de la api")
                throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
            }
        }.flowOn(Dispatchers.IO)
    }

    /** GET GENRE DETAILS */
    override suspend fun getGenreDetailsFromApi(id: Int): Flow<GenreDetails> {
        val response = apiService.getGenreDetails(id)
        if (response.isSuccessful) {
            return flowOf(
                apiService.getGenreDetails(id).body()!!).map { it.toDomain() } .catch {
                Log.e("GenreDetailsFromApi", it.message ?: "ocurrió un error en la api") }
        } else {
            Log.e("GENRE_DETAILS_API_ERROR", "Ocurrió un error en la respuesta de la api")
            throw Exception("Ocurrió un error en la respuesta de la api")
        }
    }

    override suspend fun getGenreDetails(id: Int): Flow<GenreDetails> {
        return flow {
            val genreDetailsFromApi: Flow<GenreDetails?> = try {
                getGenreDetailsFromApi(id)
            } catch (e: Exception) {
                Log.e("GenreDetails", "API_ERROR ${e.message}")
                flowOf(null)
            }
            if (genreDetailsFromApi.single() != null) {
                Log.i("GenreDetails", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    Log.i("GenreDetails", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("GenreDetails", e.message ?: "Error desconocido")
                }
                emit(genreDetailsFromApi.single()!!)
            } else {
                Log.i("GenreDetails", "API_RESPONSE: No hay datos por parte de la api")
                throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
            }
        }.flowOn(Dispatchers.IO)
    }
}