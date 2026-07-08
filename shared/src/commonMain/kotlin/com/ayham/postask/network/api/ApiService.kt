package com.ayham.postask.network.api

import com.ayham.postask.network.NetworkException
import com.ayham.postask.network.ServerExceptionType
import com.ayham.postask.network.model.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ApiService(val client: HttpClient) {

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
        explicitNulls = false
    }

    suspend inline fun <reified REQUEST, reified RESPONSE> request(
        method: HttpMethod = HttpMethod.Get,
        endpoint: String,
        body: REQUEST? = null,
        params: Map<String, String>? = null,
    ): NetworkResult<RESPONSE> {
        return try {
            val response: HttpResponse = client.request(endpoint) {
                this.method = method
                contentType(ContentType.Application.Json)
                params?.forEach { (key, value) -> parameter(key, value) }
                if (body != null && method != HttpMethod.Get) setBody(body)
            }
            val responseText = response.bodyAsText()
            if (response.status.value >= 400) {
                NetworkResult.Error(mapHttpError(response.status.value, extractMessage(responseText, response.status.description)))
            } else {
                NetworkResult.Success(json.decodeFromString<RESPONSE>(responseText))
            }
        } catch (e: Exception) {
            NetworkResult.Error(mapException(e))
        }
    }

    fun mapHttpError(statusCode: Int, message: String): NetworkException = when (statusCode) {
        401 -> NetworkException.ApiErrorException(ServerExceptionType.Unauthorized, message)
        403 -> NetworkException.ApiErrorException(ServerExceptionType.Forbidden, message)
        in 500..599 -> NetworkException.ApiErrorException(ServerExceptionType.ServerError, message)
        else -> NetworkException.ApiErrorException(ServerExceptionType.ClientError, message)
    }

    fun mapException(throwable: Throwable): NetworkException = when (throwable) {
        is NetworkException -> throwable
        else -> NetworkException.UnknownException(throwable)
    }

    fun extractMessage(body: String, fallback: String): String = try {
        json.parseToJsonElement(body).jsonObject["message"]?.jsonPrimitive?.content ?: fallback
    } catch (_: Exception) {
        fallback
    }
}
