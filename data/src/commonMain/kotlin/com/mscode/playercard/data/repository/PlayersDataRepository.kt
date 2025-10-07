package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localsource.PlayersLocalDataSource
import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersRepository
import com.mscode.playercard.remote.remotesource.PlayerCardRemoteDataSource

class PlayersDataRepository (
    private val remotePlayerCard: PlayerCardRemoteDataSource,
    private val playersLocalData: PlayersLocalDataSource
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
                    strNationality = player.strNationality,
                    strPlayer = player.strPlayer,
                    strTeam = player.strTeam,
                    dateBorn = player.dateBorn,
                    strNumber = player.strNumber,
                    strBirthLocation = player.strBirthLocation,
                    strDescriptionEN = player.strDescriptionEN,
                    strDescriptionFR = player.strDescriptionFR,
                    strGender = player.strGender,
                    strPosition = player.strPosition,
                    strFacebook = player.strFacebook,
                    strWebsite = player.strWebsite,
                    strTwitter = player.strTwitter,
                    strInstagram = player.strInstagram,
                    strYoutube = player.strYoutube,
                    strHeight = player.strHeight,
                    strWeight = player.strWeight,
                    strThumb = player.strThumb,
                    strCutout = player.strCutout,
                    strRender = player.strRender
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