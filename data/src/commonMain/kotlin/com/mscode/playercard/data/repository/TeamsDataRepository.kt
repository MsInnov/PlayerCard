package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localsource.TeamsLocalDataSource
import com.mscode.playercard.domain.models.Team
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.remote.remotesource.PlayerCardRemoteDataSource

class TeamsDataRepository(
    private val remotePlayerCard: PlayerCardRemoteDataSource,
    private val teamsLocalData: TeamsLocalDataSource
): TeamsRepository {

    override suspend fun getTeams(league: String): List<Team> {
        val localData = teamsLocalData.teams.value
        val localDataFiltered = localData.firstOrNull { it.leagueId == league }
        return if(localDataFiltered == null) {
            val leagueCorrected = league.replace(" ", "_")
            val teams = remotePlayerCard.getTeams(leagueCorrected).teams.map { team ->
                Team(
                    teamId = team.idTeam,
                    strTeam = team.strTeam,
                    intFormedYear = team.intFormedYear,
                    strLeague = team.strLeague,
                    strStadium = team.strStadium,
                    strCountry = team.strCountry,
                    strBadge = team.strBadge
                )
            }
            teamsLocalData.setTeams(
                teams = teams,
                leagueId = league
            )
            teams
        } else {
            localDataFiltered.teams
        }
    }
}