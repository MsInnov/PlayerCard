package com.mscode.playercard.data.fake

import com.mscode.playercard.remote.remoteSource.PlayerCardRemoteDataSource
import com.mscode.playercard.remote.response.LeagueResponse
import com.mscode.playercard.remote.response.LeaguesResponse
import com.mscode.playercard.remote.response.PlayerResponse
import com.mscode.playercard.remote.response.PlayersResponse
import com.mscode.playercard.remote.response.TeamResponse
import com.mscode.playercard.remote.response.TeamsResponse

class FakeRemotePlayerCard(
    private val teamsToReturn: List<TeamResponse> = emptyList(),
    private val leaguesToReturn: List<LeagueResponse> = emptyList(),
    private val playersToReturn: List<PlayerResponse> = emptyList(),
    private val shouldThrowError: Boolean = false
) : PlayerCardRemoteDataSource {

    var getLeaguesCallCount = 0
        private set

    var getPlayersCallCount = 0
        private set

    var lastTeamIdUsed: String? = null
        private set

    var getTeamsCallCount = 0
        private set

    var lastLeagueUsed: String? = null
        private set

    override suspend fun getLeagues(): LeaguesResponse {
        getLeaguesCallCount++

        if (shouldThrowError) {
            throw Exception("Network error")
        }

        return LeaguesResponse(leaguesToReturn)
    }

    override suspend fun getTeams(league: String): TeamsResponse {
        getTeamsCallCount++
        lastLeagueUsed = league

        if (shouldThrowError) {
            throw Exception("Network error")
        }

        return TeamsResponse(teamsToReturn)
    }

    override suspend fun getPlayers(teamId: String): PlayersResponse {
        getPlayersCallCount++
        lastTeamIdUsed = teamId

        if (shouldThrowError) {
            throw Exception("Network error")
        }

        return PlayersResponse(playersToReturn)
    }
}