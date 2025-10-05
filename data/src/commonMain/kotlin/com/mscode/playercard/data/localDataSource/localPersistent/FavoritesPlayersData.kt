package com.mscode.playercard.data.localDataSource.localPersistent

import com.mscode.playercard.data.localSource.FavoritesPlayersLocalDataSource
import com.mscode.playercard.domain.models.Player
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FavoritesPlayersData(
    private val storage: KeyValueStorage
): FavoritesPlayersLocalDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override fun savePlayer(player: Player) {
        val data = json.encodeToString(player.toPlayerFavorite())
        storage.putString(player.idPlayer, data)
    }

    override fun getPlayer(playerId: String): Player? {
        val data = storage.getString(playerId, "")
        if (data.isEmpty()) return null
        return runCatching {
            json.decodeFromString<PlayerFavorite>(data).toPlayer()
        }.getOrNull()
    }

    override fun getPlayers(): List<Player> {
        val data = storage.getAll()

        return data.values
            .filterIsInstance<String>()
            .filter { it.isNotEmpty() }
            .mapNotNull { jsonString ->
                runCatching {
                    json.decodeFromString<PlayerFavorite>(jsonString).toPlayer()
                }.getOrNull()
            }
    }

    override fun clearPlayer(playerId: String) {
        storage.putString(playerId, "")
    }
}

fun Player.toPlayerFavorite(): PlayerFavorite =
    PlayerFavorite(
        idPlayer = idPlayer,
        idTeam = idTeam,
        idTeam2 = idTeam2,
        idTeamNational = idTeamNational,
        idAPIfootball = idAPIfootball,
        idPlayerManager = idPlayerManager,
        idWikidata = idWikidata,
        idTransferMkt = idTransferMkt,
        idESPN = idESPN,
        strNationality = strNationality,
        strPlayer = strPlayer,
        strPlayerAlternate = strPlayerAlternate,
        strTeam = strTeam,
        strTeam2 = strTeam2,
        strSport = strSport,
        intSoccerXMLTeamID = intSoccerXMLTeamID,
        dateBorn = dateBorn,
        dateDied = dateDied,
        strNumber = strNumber,
        dateSigned = dateSigned,
        strSigning = strSigning,
        strWage = strWage,
        strOutfitter = strOutfitter,
        strKit = strKit,
        strAgent = strAgent,
        strBirthLocation = strBirthLocation,
        strEthnicity = strEthnicity,
        strStatus = strStatus,
        strDescriptionEN = strDescriptionEN,
        strDescriptionDE = strDescriptionDE,
        strDescriptionFR = strDescriptionFR,
        strDescriptionCN = strDescriptionCN,
        strDescriptionIT = strDescriptionIT,
        strDescriptionJP = strDescriptionJP,
        strDescriptionRU = strDescriptionRU,
        strDescriptionES = strDescriptionES,
        strDescriptionPT = strDescriptionPT,
        strDescriptionSE = strDescriptionSE,
        strDescriptionNL = strDescriptionNL,
        strDescriptionHU = strDescriptionHU,
        strDescriptionNO = strDescriptionNO,
        strDescriptionIL = strDescriptionIL,
        strDescriptionPL = strDescriptionPL,
        strGender = strGender,
        strSide = strSide,
        strPosition = strPosition,
        strCollege = strCollege,
        strFacebook = strFacebook,
        strWebsite = strWebsite,
        strTwitter = strTwitter,
        strInstagram = strInstagram,
        strYoutube = strYoutube,
        strHeight = strHeight,
        strWeight = strWeight,
        intLoved = intLoved,
        strThumb = strThumb,
        strPoster = strPoster,
        strCutout = strCutout,
        strRender = strRender,
        strBanner = strBanner,
        strFanart1 = strFanart1,
        strFanart2 = strFanart2,
        strFanart3 = strFanart3,
        strFanart4 = strFanart4,
        strCreativeCommons = strCreativeCommons,
        strLocked = strLocked
    )

fun PlayerFavorite.toPlayer(): Player =
    Player(
        idPlayer = idPlayer,
        idTeam = idTeam,
        idTeam2 = idTeam2,
        idTeamNational = idTeamNational,
        idAPIfootball = idAPIfootball,
        idPlayerManager = idPlayerManager,
        idWikidata = idWikidata,
        idTransferMkt = idTransferMkt,
        idESPN = idESPN,
        strNationality = strNationality,
        strPlayer = strPlayer,
        strPlayerAlternate = strPlayerAlternate,
        strTeam = strTeam,
        strTeam2 = strTeam2,
        strSport = strSport,
        intSoccerXMLTeamID = intSoccerXMLTeamID,
        dateBorn = dateBorn,
        dateDied = dateDied,
        strNumber = strNumber,
        dateSigned = dateSigned,
        strSigning = strSigning,
        strWage = strWage,
        strOutfitter = strOutfitter,
        strKit = strKit,
        strAgent = strAgent,
        strBirthLocation = strBirthLocation,
        strEthnicity = strEthnicity,
        strStatus = strStatus,
        strDescriptionEN = strDescriptionEN,
        strDescriptionDE = strDescriptionDE,
        strDescriptionFR = strDescriptionFR,
        strDescriptionCN = strDescriptionCN,
        strDescriptionIT = strDescriptionIT,
        strDescriptionJP = strDescriptionJP,
        strDescriptionRU = strDescriptionRU,
        strDescriptionES = strDescriptionES,
        strDescriptionPT = strDescriptionPT,
        strDescriptionSE = strDescriptionSE,
        strDescriptionNL = strDescriptionNL,
        strDescriptionHU = strDescriptionHU,
        strDescriptionNO = strDescriptionNO,
        strDescriptionIL = strDescriptionIL,
        strDescriptionPL = strDescriptionPL,
        strGender = strGender,
        strSide = strSide,
        strPosition = strPosition,
        strCollege = strCollege,
        strFacebook = strFacebook,
        strWebsite = strWebsite,
        strTwitter = strTwitter,
        strInstagram = strInstagram,
        strYoutube = strYoutube,
        strHeight = strHeight,
        strWeight = strWeight,
        intLoved = intLoved,
        strThumb = strThumb,
        strPoster = strPoster,
        strCutout = strCutout,
        strRender = strRender,
        strBanner = strBanner,
        strFanart1 = strFanart1,
        strFanart2 = strFanart2,
        strFanart3 = strFanart3,
        strFanart4 = strFanart4,
        strCreativeCommons = strCreativeCommons,
        strLocked = strLocked
    )

@Serializable
data class PlayerFavorite (
    val idPlayer: String,
    val idTeam: String,
    val idTeam2: String?,
    val idTeamNational: String?,
    val idAPIfootball: String?,
    val idPlayerManager: String?,
    val idWikidata: String?,
    val idTransferMkt: String?,
    val idESPN: String?,
    val strNationality: String?,
    val strPlayer: String?,
    val strPlayerAlternate: String?,
    val strTeam: String?,
    val strTeam2: String?,
    val strSport: String?,
    val intSoccerXMLTeamID: String?,
    val dateBorn: String?,
    val dateDied: String?,
    val strNumber: String?,
    val dateSigned: String?,
    val strSigning: String?,
    val strWage: String?,
    val strOutfitter: String?,
    val strKit: String?,
    val strAgent: String?,
    val strBirthLocation: String?,
    val strEthnicity: String?,
    val strStatus: String?,
    val strDescriptionEN: String?,
    val strDescriptionDE: String?,
    val strDescriptionFR: String?,
    val strDescriptionCN: String?,
    val strDescriptionIT: String?,
    val strDescriptionJP: String?,
    val strDescriptionRU: String?,
    val strDescriptionES: String?,
    val strDescriptionPT: String?,
    val strDescriptionSE: String?,
    val strDescriptionNL: String?,
    val strDescriptionHU: String?,
    val strDescriptionNO: String?,
    val strDescriptionIL: String?,
    val strDescriptionPL: String?,
    val strGender: String?,
    val strSide: String?,
    val strPosition: String?,
    val strCollege: String?,
    val strFacebook: String?,
    val strWebsite: String?,
    val strTwitter: String?,
    val strInstagram: String?,
    val strYoutube: String?,
    val strHeight: String?,
    val strWeight: String?,
    val intLoved: String?,
    val strThumb: String?,
    val strPoster: String?,
    val strCutout: String?,
    val strRender: String?,
    val strBanner: String?,
    val strFanart1: String?,
    val strFanart2: String?,
    val strFanart3: String?,
    val strFanart4: String?,
    val strCreativeCommons: String?,
    val strLocked: String?
)