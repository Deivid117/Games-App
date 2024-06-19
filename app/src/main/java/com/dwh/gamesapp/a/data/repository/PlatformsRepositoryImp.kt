package com.dwh.gamesapp.a.data.repository

import android.util.Log
import com.dwh.gamesapp.a.data.api.ApiService
import com.dwh.gamesapp.a.data.model.response.platform_details.PlatformDetailsResponse
import com.dwh.gamesapp.a.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.a.domain.model.platform_details.toDomain
import com.dwh.gamesapp.a.domain.model.plattform.PlattformResults
import com.dwh.gamesapp.a.domain.model.plattform.toDomain
import com.dwh.gamesapp.a.domain.repository.PlatformsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class PlatformsRepositoryImp @Inject constructor(
    private val apiService: ApiService
): PlatformsRepository {

    /** GET PLATFORMS */
    override suspend fun getPlatformsFromApi(): Flow<List<PlattformResults>> {
        val response = apiService.getPlatforms()
        if (response.isSuccessful) {
            return flowOf(
                apiService.getPlatforms().body()!!.results.map { it.toDomain() }).catch {
                Log.e("PlatformsFromApi", it.message ?: "ocurrió un error en la api")
            }
        } else {
            Log.e("PLATFORMS_API_ERROR", "Ocurrió un error en la respuesta de la api")
            throw Exception("Ocurrió un error en la respuesta de la api")
        }
    }

    override suspend fun getAllPlatforms(): Flow<List<PlattformResults>> {
        return flow {
            val platformsFromApi: Flow<List<PlattformResults>> = try {
                getPlatformsFromApi()
            } catch (e: Exception) {
                Log.e("AllPlatforms", "API_ERROR ${e.message}")
                flowOf(emptyList<PlattformResults>())
                //throw Exception("API_ERROR ${e.message}")
            }
            if (platformsFromApi.single().isNotEmpty()) { // single() convierte el flow<list> en list
                Log.i("AllPlatforms", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    Log.i("AllPlatforms", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("AllPlatforms", e.message ?: "Error desconocido")
                }
                try {

                } catch (e: Exception) {
                    Log.e("AllPlatforms", e.message ?: "Error desconocido")
                }
                emit(platformsFromApi.single())
            } else {
                Log.i("AllPlatforms", "API_RESPONSE: No hay datos por parte de la api")
                throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
            }
        }.flowOn(Dispatchers.IO)
    }

    /** GET PLATFORM DETAILS */
    override suspend fun getPlatformDetailsFromApi(id: Int): Flow<PlatformDetails> {
        val response: Response<PlatformDetailsResponse> = apiService.getPlatformsDetails(id)
        if (response.isSuccessful) {
            return flowOf(
                apiService.getPlatformsDetails(id).body()!!).map { it.toDomain() } .catch {
                Log.e("PlatformDetailsFromApi", it.message ?: "ocurrió un error en la api") }
        } else {
            Log.e("PLATFORM_DETAILS_API_ERROR", "Ocurrió un error en la respuesta de la api")
            throw Exception("Ocurrió un error en la respuesta de la api")
        }
    }

    override suspend fun getPlatformDetails(id: Int): Flow<PlatformDetails> {
        return flow {
            val gamesDetailsFromApi: Flow<PlatformDetails?> = try {
                getPlatformDetailsFromApi(id)
            } catch (e: Exception) {
                Log.e("PlatformDetails", "API_ERROR ${e.message}")
                flowOf(null)
            }
            if (gamesDetailsFromApi.single() != null) {
                Log.i("PlatformDetails", "API_RESPONSE: Se obtuvieron los datos de la api")
                try {
                    //deleteAllGames()
                    Log.i("PlatformDetails", "ROOM_RESPONSE: Se borraron los datos")
                } catch (e: Exception) {
                    Log.e("PlatformDetails", e.message ?: "Error desconocido")
                }
                emit(gamesDetailsFromApi.single()!!)
            } else {
                Log.i("PlatformDetails", "API_RESPONSE: No hay datos por parte de la api")
                //emit(gamesDetailsFromApi.single())
                throw Exception("ERROR, no hay datos por parte de la api ni la base de datos")
            }
        }.flowOn(Dispatchers.IO)
    }
}