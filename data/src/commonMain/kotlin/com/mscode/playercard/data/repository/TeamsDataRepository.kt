package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localDataSource.localVolatile.TeamsLocalData
import com.mscode.playercard.data.localSource.TeamsLocalDataSource
import com.mscode.playercard.domain.models.Team
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.remote.RemotePlayerCard
import com.mscode.playercard.remote.remoteSource.PlayerCardRemoteDataSource

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
                    strDivision = team.strDivision,
                    strStadium = team.strStadium,
                    strLocation = team.strLocation,
                    intStadiumCapacity = team.intStadiumCapacity,
                    strWebsite = team.strWebsite,
                    strFacebook = team.strFacebook,
                    strTwitter = team.strTwitter,
                    strInstagram = team.strInstagram,
                    strDescriptionEN = team.strDescriptionEN,
                    strDescriptionFR = team.strDescriptionFR,
                    strGender = team.strGender,
                    strCountry = team.strCountry,
                    strBadge = team.strBadge,
                    strLogo = team.strLogo,
                    strFanart1 = team.strFanart1,
                    strEquipment = team.strEquipment
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