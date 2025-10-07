package com.mscode.playercard.data.localdatasource.localpersistent

import kotlinx.serialization.Serializable

@Serializable
data class PlayerFavorite (
    val idPlayer: String,
    val strNationality: String?,
    val strPlayer: String?,
    val strTeam: String?,
    val dateBorn: String?,
    val strNumber: String?,
    val strBirthLocation: String?,
    val strDescriptionEN: String?,
    val strDescriptionFR: String?,
    val strGender: String?,
    val strPosition: String?,
    val strFacebook: String?,
    val strWebsite: String?,
    val strTwitter: String?,
    val strInstagram: String?,
    val strYoutube: String?,
    val strHeight: String?,
    val strWeight: String?,
    val strThumb: String?,
    val strCutout: String?,
    val strRender: String?
)