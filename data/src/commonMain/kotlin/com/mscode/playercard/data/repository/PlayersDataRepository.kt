package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localDataSource.localVolatile.PlayersLocalData
import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersRepository
import com.mscode.playercard.remote.RemotePlayerCard

class PlayersDataRepository (
    private val remotePlayerCard: RemotePlayerCard,
    private val playersLocalData: PlayersLocalData
): PlayersRepository {

    override suspend fun getPlayers(
        teamId: String
    ): List<Player> {
        val localData = playersLocalData.players.value
        val localDataFiltered = localData.firstOrNull { it.teamsId == teamId }
        return if(localDataFiltered == null) {
            val players = remotePlayerCard.getPlayers(teamId).player.map { player ->
                Player(
                    idPlayer = player.idPlayer,
                    idTeam = player.idTeam,
                    idTeam2 = player.idTeam2,
                    idTeamNational = player.idTeamNational,
                    idAPIfootball = player.idAPIfootball,
                    idPlayerManager = player.idPlayerManager,
                    idWikidata = player.idWikidata,
                    idTransferMkt = player.idTransferMkt,
                    idESPN = player.idESPN,
                    strNationality = player.strNationality,
                    strPlayer = player.strPlayer,
                    strPlayerAlternate = player.strPlayerAlternate,
                    strTeam = player.strTeam,
                    strTeam2 = player.strTeam2,
                    strSport = player.strSport,
                    intSoccerXMLTeamID = player.intSoccerXMLTeamID,
                    dateBorn = player.dateBorn,
                    dateDied = player.dateDied,
                    strNumber = player.strNumber,
                    dateSigned = player.dateSigned,
                    strSigning = player.strSigning,
                    strWage = player.strWage,
                    strOutfitter = player.strOutfitter,
                    strKit = player.strKit,
                    strAgent = player.strAgent,
                    strBirthLocation = player.strBirthLocation,
                    strEthnicity = player.strEthnicity,
                    strStatus = player.strStatus,
                    strDescriptionEN = player.strDescriptionEN,
                    strDescriptionDE = player.strDescriptionDE,
                    strDescriptionFR = player.strDescriptionFR,
                    strDescriptionCN = player.strDescriptionCN,
                    strDescriptionIT = player.strDescriptionIT,
                    strDescriptionJP = player.strDescriptionJP,
                    strDescriptionRU = player.strDescriptionRU,
                    strDescriptionES = player.strDescriptionES,
                    strDescriptionPT = player.strDescriptionPT,
                    strDescriptionSE = player.strDescriptionSE,
                    strDescriptionNL = player.strDescriptionNL,
                    strDescriptionHU = player.strDescriptionHU,
                    strDescriptionNO = player.strDescriptionNO,
                    strDescriptionIL = player.strDescriptionIL,
                    strDescriptionPL = player.strDescriptionPL,
                    strGender = player.strGender,
                    strSide = player.strSide,
                    strPosition = player.strPosition,
                    strCollege = player.strCollege,
                    strFacebook = player.strFacebook,
                    strWebsite = player.strWebsite,
                    strTwitter = player.strTwitter,
                    strInstagram = player.strInstagram,
                    strYoutube = player.strYoutube,
                    strHeight = player.strHeight,
                    strWeight = player.strWeight,
                    intLoved = player.intLoved,
                    strThumb = player.strThumb,
                    strPoster = player.strPoster,
                    strCutout = player.strCutout,
                    strRender = player.strRender,
                    strBanner = player.strBanner,
                    strFanart1 = player.strFanart1,
                    strFanart2 = player.strFanart2,
                    strFanart3 = player.strFanart3,
                    strFanart4 = player.strFanart4,
                    strCreativeCommons = player.strCreativeCommons,
                    strLocked = player.strLocked
                )
            }
            playersLocalData.setPlayers(
                teamsId = teamId ,
                players = players
            )
            players
        } else {
            localDataFiltered.players
        }
    }
}