package com.mscode.playercard.data.fake

import com.mscode.playercard.data.localDataSource.localVolatile.TeamsByLeagueLocalData
import com.mscode.playercard.data.localSource.TeamsLocalDataSource
import com.mscode.playercard.domain.models.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeTeamsLocalData(
    initialTeams: List<TeamsByLeagueLocalData> = emptyList()
) : TeamsLocalDataSource {

    private val _teams = MutableStateFlow(initialTeams)
    override val teams: StateFlow<List<TeamsByLeagueLocalData>> = _teams

    var setTeamsCallCount = 0
        private set

    var lastLeagueIdSet: String? = null
        private set

    override fun setTeams(teams: List<Team>, leagueId: String) {
        setTeamsCallCount++
        lastLeagueIdSet = leagueId

        val existingLeagues = _teams.value.toMutableList()
        val existingIndex = existingLeagues.indexOfFirst { it.leagueId == leagueId }

        if (existingIndex >= 0) {
            existingLeagues[existingIndex] = TeamsByLeagueLocalData(leagueId, teams)
        } else {
            existingLeagues.add(TeamsByLeagueLocalData(leagueId, teams))
        }

        _teams.value = existingLeagues
    }
}