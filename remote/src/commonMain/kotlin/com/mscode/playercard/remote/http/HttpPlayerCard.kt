package com.mscode.playercard.remote.http

import com.mscode.playercard.remote.response.LeaguesResponse
import com.mscode.playercard.remote.response.PlayersResponse
import com.mscode.playercard.remote.response.TeamsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class HttpPlayerCard(
    private val httpClient: HttpClient,
){
    suspend fun getLeagues(): LeaguesResponse =
        httpClient
            .get("all_leagues.php") {
                contentType(ContentType.Application.Json)
            }.body()

    suspend fun getTeams(league: String): TeamsResponse =
        httpClient
            .get("search_all_teams.php?l=$league") {
                contentType(ContentType.Application.Json)
            }.body()

    suspend fun getPlayers(teamId: String): PlayersResponse =
        httpClient
            .get("lookup_all_players.php?id=$teamId") {
                contentType(ContentType.Application.Json)
            }.body()
}