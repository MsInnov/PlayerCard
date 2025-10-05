package com.mscode.playercard.remote.remoteSource

import com.mscode.playercard.remote.response.LeaguesResponse
import com.mscode.playercard.remote.response.PlayersResponse
import com.mscode.playercard.remote.response.TeamsResponse

interface PlayerCardRemoteDataSource {

    suspend fun getLeagues(): LeaguesResponse
    suspend fun getTeams(league: String): TeamsResponse
    suspend fun getPlayers(teamId: String): PlayersResponse

}