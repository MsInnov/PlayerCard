package com.mscode.playercard.data.localsource

import com.mscode.playercard.data.localdatasource.localvolatile.TeamsByLeagueLocalData
import com.mscode.playercard.domain.models.Team
import kotlinx.coroutines.flow.StateFlow

interface TeamsLocalDataSource {

    val teams: StateFlow<List<TeamsByLeagueLocalData>>
    fun setTeams(teams: List<Team>, leagueId: String)

}