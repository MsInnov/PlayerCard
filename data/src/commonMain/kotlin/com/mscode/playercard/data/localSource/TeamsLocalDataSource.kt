package com.mscode.playercard.data.localSource

import com.mscode.playercard.data.localDataSource.localVolatile.TeamsByLeagueLocalData
import com.mscode.playercard.domain.models.Team
import kotlinx.coroutines.flow.StateFlow

interface TeamsLocalDataSource {

    val teams: StateFlow<List<TeamsByLeagueLocalData>>
    fun setTeams(teams: List<Team>, leagueId: String)

}