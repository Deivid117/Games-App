package com.dwh.gamesapp.platforms.data.repository

import android.util.Log
import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.a.data.model.response.platform_details.PlatformDetailsResponse
import com.dwh.gamesapp.a.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.a.domain.model.platform_details.toDomain
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.BaseResponse
import com.dwh.gamesapp.platforms.domain.model.PlatformResults
import com.dwh.gamesapp.platforms.domain.model.toDomain
import com.dwh.gamesapp.platforms.domain.repository.PlatformsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

class PlatformsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PlatformsRepository, BaseResponse() {

    /** GET PLATFORMS */
    override suspend fun getPlatformsFromApi(): Flow<Resource<PlatformResults>> =
        safeApiCall {
            apiService.getPlatforms()
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }

    /*override suspend fun getAllPlatforms(): Flow<List<PlatformResults>> {
        return flow {
            val platformsFromApi: Flow<List<PlatformResults>> = try {
                getPlatformsFromApi()
            } catch (e: Exception) {
                Log.e("AllPlatforms", "API_ERROR ${e.message}")
                flowOf(emptyList<PlatformResults>())
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
    }*/

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