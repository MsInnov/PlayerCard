package com.mscode.playercard.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscode.playercard.domain.usecase.GetTeamsUseCase
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.models.TeamUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TeamsViewModel : ViewModel(), KoinComponent {

    private val getTeamsUseCase: GetTeamsUseCase by inject<GetTeamsUseCase>()

    private val _uiModel: MutableStateFlow<ScreenStateUiModel<List<TeamUi>>> = MutableStateFlow(
        ScreenStateUiModel.Loading("")
    )
    val uiModel: StateFlow<ScreenStateUiModel<List<TeamUi>>> = _uiModel

    fun onEvent(teams: TeamsEvent) {
        when(teams) {
            is TeamsEvent.GetTeam -> {
                viewModelScope.launch {
                    val resultTeams = getTeamsUseCase.invoke(teams.league)
                    _uiModel.value = resultTeams.fold(
                        onSuccess = {
                            ScreenStateUiModel.Success(
                                content = it.map { team ->
                                    TeamUi(
                                        teamId = team.teamId,
                                        strTeam = team.strTeam,
                                        intFormedYear = team.intFormedYear,
                                        strLeague = team.strLeague,
                                        strStadium = team.strStadium,
                                        strCountry = team.strCountry,
                                        strBadge = team.strBadge
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

    sealed class TeamsEvent{
        data class GetTeam(val league: String): TeamsEvent()
    }
}