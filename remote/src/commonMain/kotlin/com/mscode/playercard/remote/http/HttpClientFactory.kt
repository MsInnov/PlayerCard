package com.mscode.playercard.remote.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory internal constructor(
    private val brotliEncoder: BrotliEncoder? = null,
    private val httpClientEngine: HttpClientEngine? = null,
) {
    fun createClient(
        baseUrl: String,
        json: Json,
    ): HttpClient = createBaseClient().config {
        install(ContentEncoding) {
            gzip()
            deflate()
            brotliEncoder?.let { customEncoder(it, null) }
        }

        install(DefaultRequest) {
            url(baseUrl)
        }
        install(ContentNegotiation) {
            json(json)
        }
    }

    private fun createBaseClient() =
        if (httpClientEngine != null) HttpClient(httpClientEngine) else HttpClient()
}
