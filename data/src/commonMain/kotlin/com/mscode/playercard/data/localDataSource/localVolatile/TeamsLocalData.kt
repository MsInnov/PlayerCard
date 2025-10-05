package com.mscode.playercard.data.localDataSource.localVolatile

import com.mscode.playercard.data.localSource.TeamsLocalDataSource
import com.mscode.playercard.domain.models.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TeamsLocalData: TeamsLocalDataSource {

    private val _teams: MutableStateFlow<List<TeamsByLeagueLocalData>> =  MutableStateFlow(emptyList())

    override val teams: StateFlow<List<TeamsByLeagueLocalData>>
        get() = _teams

    override fun setTeams(teams: List<Team>, leagueId: String) {
        _teams.update { teamsByLeagueLocalData ->
            val localDataUpdate = teamsByLeagueLocalData.toMutableList()
            localDataUpdate.add(
                TeamsByLeagueLocalData(
                    teams = teams,
                    leagueId = leagueId
                )
            )
            localDataUpdate
        }
    }

}

data class TeamsByLeagueLocalData(
    val leagueId: String,
    val teams: List<Team>
)