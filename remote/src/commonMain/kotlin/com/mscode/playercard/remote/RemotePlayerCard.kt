package com.mscode.playercard.remote

import com.mscode.playercard.remote.http.HttpPlayerCardFactory
import com.mscode.playercard.remote.response.LeaguesResponse
import com.mscode.playercard.remote.response.PlayersResponse
import com.mscode.playercard.remote.response.TeamsResponse

class RemotePlayerCard(
    private val httpPlayerCardFactory: HttpPlayerCardFactory
) {
    suspend fun getLeagues(): LeaguesResponse =
        httpPlayerCardFactory.createHttpPlayerCard().getLeagues()

    suspend fun getTeams(league: String): TeamsResponse =
        httpPlayerCardFactory.createHttpPlayerCard().getTeams(league)

    suspend fun getPlayers(teamId: String): PlayersResponse =
        httpPlayerCardFactory.createHttpPlayerCard().getPlayers(teamId)
}