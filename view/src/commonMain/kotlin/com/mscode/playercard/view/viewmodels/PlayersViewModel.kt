package com.mscode.playercard.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscode.playercard.domain.usecase.GetPlayersUseCase
import com.mscode.playercard.view.models.PlayerUi
import com.mscode.playercard.view.models.ScreenStateUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayersViewModel : ViewModel(), KoinComponent {

    private val getPlayersUseCase: GetPlayersUseCase by inject<GetPlayersUseCase>()

    private val _uiModel: MutableStateFlow<ScreenStateUiModel<List<PlayerUi>>> = MutableStateFlow(
        ScreenStateUiModel.Loading("")
    )
    val uiModel: StateFlow<ScreenStateUiModel<List<PlayerUi>>> = _uiModel

    fun onEvent(teams: PlayersEvent) {
        when(teams) {
            is PlayersEvent.GetPlayers -> {
                viewModelScope.launch {
                    val resultTeams = getPlayersUseCase.invoke(teams.teamId)
                    _uiModel.value = resultTeams.fold(
                        onSuccess = {
                            ScreenStateUiModel.Success(
                                content = it.map { player ->
                                    PlayerUi(
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
                            )
                        },
                        onFailure = {
                            ScreenStateUiModel.Error(it.message?:"ERROR")
                        }
                    )
                }
            }
        }
    }

    sealed class PlayersEvent{
        data class GetPlayers(val teamId: String): PlayersEvent()
    }
}