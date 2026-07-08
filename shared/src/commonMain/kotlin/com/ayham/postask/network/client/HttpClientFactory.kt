package com.ayham.postask.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    fun create(baseUrl: String, engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            defaultRequest {
                url.takeFrom(URLBuilder(baseUrl).apply {
                    encodedPath += url.encodedPath
                })
                contentType(ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                        isLenient = true
                        coerceInputValues = true
                    }
                )
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 100_000L
                requestTimeoutMillis = 100_000L
            }
        }
    }
}
