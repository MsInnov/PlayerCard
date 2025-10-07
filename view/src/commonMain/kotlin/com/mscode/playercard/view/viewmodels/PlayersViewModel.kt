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