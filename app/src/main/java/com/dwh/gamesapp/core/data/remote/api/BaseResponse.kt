package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.remote.model.ApiErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseResponse {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Flow<Resource<T>> {
        return flow {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()!!, code = response.code()))
                } else {
                    val errorResponse = convertErrorBody(response.errorBody())
                    emit(
                        Resource.Error(
                            message = errorResponse?.error ?: "Inténtelo más tarde",
                            code = response.code()
                        )
                    )
                }
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.message ?: "Error del servidor"))
            } catch (e: IOException) {
                emit(Resource.Error("Por favor, revisa tu conexión a internet"))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "Error desconocido"))
            }

        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ApiErrorResponse? {
        return if (errorBody != null) {
            try {
                val type = object : TypeToken<ApiErrorResponse>() {}.type
                Gson().fromJson<ApiErrorResponse>(errorBody.string(), type)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }
}