package com.mscode.playercard.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscode.playercard.domain.models.League
import com.mscode.playercard.domain.usecase.GetLeaguesUseCase
import com.mscode.playercard.view.models.ScreenStateUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LeaguesViewModel: ViewModel(), KoinComponent {

    private val getLeaguesUseCase: GetLeaguesUseCase by inject<GetLeaguesUseCase>()

    val uiModel: StateFlow<ScreenStateUiModel<List<String>>> =
        flow {
            emit(getLeaguesUseCase.invoke())
        }.map { result ->
            result.fold(
                onSuccess = {
                    ScreenStateUiModel.Success(
                        content = it.map { league: League ->
                            league.league
                        }
                    )
                },
                onFailure = {
                    ScreenStateUiModel.Error(it.message?:"ERROR")
                }
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ScreenStateUiModel.Loading("loading")
        )
}