package com.ayham.postask.network.mock

import com.ayham.postask.network.constant.Endpoints
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.headersOf
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class FakePosServer {

    private var transientFailureUsed = false
    private val json = Json { ignoreUnknownKeys = true }

    fun engine(): HttpClientEngine = MockEngine { request ->
        when {
            request.method == HttpMethod.Get && request.url.encodedPath == Endpoints.Catalog.PRODUCTS ->
                respondCatalog()

            request.method == HttpMethod.Post && request.url.encodedPath == Endpoints.Orders.SYNC ->
                respondSync(request)

            else -> respond(content = "", status = HttpStatusCode.NotFound)
        }
    }

    private suspend fun MockRequestHandleScope.respondCatalog(): HttpResponseData {
        delay(1200)
        return respond(
            content = CATALOG_JSON,
            status = HttpStatusCode.OK,
            headers = jsonHeaders,
        )
    }

    private fun MockRequestHandleScope.respondSync(request: HttpRequestData): HttpResponseData {
        if (!transientFailureUsed) {
            transientFailureUsed = true
            return respond(
                content = """{"message":"transient failure"}""",
                status = HttpStatusCode.InternalServerError,
                headers = jsonHeaders,
            )
        }

        val orderId = extractOrderId(request)
        return respond(
            content = """{"id":"$orderId","status":"SYNCED"}""",
            status = HttpStatusCode.OK,
            headers = jsonHeaders,
        )
    }

    private fun extractOrderId(request: HttpRequestData): String {
        val body = (request.body as? TextContent)?.text.orEmpty()
        return runCatching {
            json.parseToJsonElement(body).jsonObject["id"]?.jsonPrimitive?.content
        }.getOrNull().orEmpty()
    }

    private companion object {
        val jsonHeaders = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

        val CATALOG_JSON = """
            [
              { "id": "p1", "name": "Espresso", "priceCents": 350, "taxable": true, "stock": 20 },
              { "id": "p2", "name": "Cappuccino", "priceCents": 450, "taxable": true, "stock": 15 },
              { "id": "p3", "name": "Butter Croissant", "priceCents": 500, "taxable": false, "stock": 8 },
              { "id": "p4", "name": "Sesame Bagel", "priceCents": 400, "taxable": false, "stock": 0 },
              { "id": "p5", "name": "Club Sandwich", "priceCents": 1200, "taxable": true, "stock": 10 },
              { "id": "p6", "name": "Spring Water", "priceCents": 200, "taxable": false, "stock": 30 },
              { "id": "p7", "name": "Ceramic Mug", "priceCents": 2500, "taxable": true, "stock": 5 }
            ]
        """.trimIndent()
    }
}
