package com.mscode.playercard.remote.http

import io.ktor.client.HttpClient

private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/123/"

class HttpPlayerCardFactory(
    httpClientFactory: HttpClientFactory,
) {
    private val httpClient: HttpClient = httpClientFactory.createClient(
        baseUrl = BASE_URL,
        json = createPlayerCardJsonConfiguration(),
    )

    internal fun createHttpPlayerCard(): HttpPlayerCard =
        HttpPlayerCard(httpClient)
}