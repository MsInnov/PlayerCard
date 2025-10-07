package com.mscode.playercard.data.localdatasource.localpersistent

import com.mscode.playercard.domain.models.Player

fun Player.toPlayerFavorite(): PlayerFavorite =
    PlayerFavorite(
        idPlayer = idPlayer,
        strNationality = strNationality,
        strPlayer = strPlayer,
        strTeam = strTeam,
        dateBorn = dateBorn,
        strNumber = strNumber,
        strBirthLocation = strBirthLocation,
        strDescriptionEN = strDescriptionEN,
        strDescriptionFR = strDescriptionFR,
        strGender = strGender,
        strPosition = strPosition,
        strFacebook = strFacebook,
        strWebsite = strWebsite,
        strTwitter = strTwitter,
        strInstagram = strInstagram,
        strYoutube = strYoutube,
        strHeight = strHeight,
        strWeight = strWeight,
        strThumb = strThumb,
        strCutout = strCutout,
        strRender = strRender
    )

fun PlayerFavorite.toPlayer(): Player =
    Player(
        idPlayer = idPlayer,
        strNationality = strNationality,
        strPlayer = strPlayer,
        strTeam = strTeam,
        dateBorn = dateBorn,
        strNumber = strNumber,
        strBirthLocation = strBirthLocation,
        strDescriptionEN = strDescriptionEN,
        strDescriptionFR = strDescriptionFR,
        strGender = strGender,
        strPosition = strPosition,
        strFacebook = strFacebook,
        strWebsite = strWebsite,
        strTwitter = strTwitter,
        strInstagram = strInstagram,
        strYoutube = strYoutube,
        strHeight = strHeight,
        strWeight = strWeight,
        strThumb = strThumb,
        strCutout = strCutout,
        strRender = strRender
    )