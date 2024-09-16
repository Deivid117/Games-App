package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.remote.model.ApiErrorResponse
import com.dwh.gamesapp.core.presentation.state.DataState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Flow<DataState<T>> = flow {
        emit(DataState.Loading)

        val response = apiToBeCalled()

        if (response.isSuccessful) {
            if (response.body() != null) emit(DataState.Success(data = response.body()!!, code = response.code()))
            else emit(
                DataState.Error(
                    errorMessage = "Sin Datos Disponibles",
                    errorDescription = "No pudimos encontrar la información solicitada",
                    code = response.code()
                )
            )
        } else {
            val errorResponse = convertErrorBody(response.errorBody())
            emit(
                DataState.Error(
                    errorMessage = "Error de Conexión",
                    errorDescription = errorResponse?.error
                        ?: "Ocurrió un error al solicitar los datos, por favor inténtelo más tarde",
                    code = response.code()
                )
            )
        }
    }.flowOn(Dispatchers.IO).catch { throwable ->
        when (throwable) {
            is IOException -> emit(
                DataState.Error(
                    errorMessage = "Sin Conexión a Internet",
                    errorDescription = "Por favor, verifica tu conexión a internet y vuelve a intentarlo",
                    code = throwable.hashCode()
                )
            )
            is HttpException -> emit(
                DataState.Error(
                    errorMessage = "Error del Servidor",
                    errorDescription = throwable.message
                        ?: "Estamos experimentando problemas con el servidor. Por favor, inténtalo más tarde",
                    code = throwable.code()
                )
            )
            else -> emit(
                DataState.Error(
                    errorMessage = "Algo Salió Mal",
                    errorDescription = throwable.message ?: "Error desconocido",
                    code = throwable.hashCode()
                )
            )
        }
    }

    suspend fun <T> safeRoomCall(daoToBeCalled: suspend () -> T): Flow<DataState<T>> = flow {
        emit(DataState.Loading)
        emit(DataState.Success(data = daoToBeCalled(), code = daoToBeCalled.hashCode()))
    }.flowOn(Dispatchers.IO).catch { throwable ->
        emit(
            DataState.Error(
                errorMessage = "Algo Salió Mal",
                errorDescription = throwable.message ?: "Error desconocido",
                code = throwable.hashCode()
            )
        )
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




