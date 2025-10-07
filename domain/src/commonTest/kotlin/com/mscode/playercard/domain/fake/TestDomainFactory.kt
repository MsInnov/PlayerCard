package com.mscode.playercard.domain.fake

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.models.Team

object TestDomainFactory {
    fun createPlayer(
        idPlayer: String = "1",
        strPlayer: String = "Lionel Messi",
        strNationality: String = "Argentina",
        strPosition: String = "Forward"
    ) = Player(
        idPlayer = idPlayer,
        strNationality = strNationality,
        strPlayer = strPlayer,
        strTeam = "PSG",
        dateBorn = "1987-06-24",
        strNumber = "30",
        strBirthLocation = "Rosario, Argentina",
        strDescriptionEN = "One of the greatest players",
        strDescriptionFR = null,
        strGender = "Male",
        strPosition = strPosition,
        strFacebook = null,
        strWebsite = null,
        strTwitter = null,
        strInstagram = null,
        strYoutube = null,
        strHeight = "170 cm",
        strWeight = "72 kg",
        strThumb = "https://example.com/thumb.jpg",
        strCutout = "https://example.com/cutout.png",
        strRender = null
    )

    fun createTeam(
        teamId: String = "133604",
        strTeam: String = "Paris Saint-Germain",
        strLeague: String = "French Ligue 1",
        strStadium: String = "Parc des Princes",
        strCountry: String = "France"
    ) = Team(
        teamId = teamId,
        strTeam = strTeam,
        intFormedYear = "1970",
        strLeague = strLeague,
        strStadium = strStadium,
        strCountry = strCountry,
        strBadge = "https://example.com/badge.png"
    )
}