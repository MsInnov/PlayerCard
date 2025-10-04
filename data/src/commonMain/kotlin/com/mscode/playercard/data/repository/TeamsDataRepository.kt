package com.mscode.playercard.data.repository

import com.mscode.playercard.domain.models.Teams
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.remote.RemotePlayerCard

class TeamsDataRepository(
    private val remotePlayerCard: RemotePlayerCard
): TeamsRepository {

    override suspend fun getTeams(league: String): List<Teams> {
        val leagueCorrected = league.replace(" ", "_")
        return remotePlayerCard.getTeams(leagueCorrected).teams.map { team ->
            Teams(
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
    }
}