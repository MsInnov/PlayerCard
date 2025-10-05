package com.mscode.playercard.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mscode.playercard.domain.usecase.GetPlayerFavoriteUseCase
import com.mscode.playercard.domain.usecase.GetPlayersFavoriteUseCase
import com.mscode.playercard.domain.usecase.RemovePlayerFavoriteUseCase
import com.mscode.playercard.domain.usecase.SavePlayerFavoriteUseCase
import com.mscode.playercard.view.models.PlayerUi
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.models.toPlayer
import com.mscode.playercard.view.models.toPlayerUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlayersFavoriteViewModel: ViewModel(), KoinComponent {

    private val getPlayerFavoriteUseCase: GetPlayerFavoriteUseCase by inject<GetPlayerFavoriteUseCase>()
    private val savePlayerFavoriteUseCase: SavePlayerFavoriteUseCase by inject<SavePlayerFavoriteUseCase>()
    private val removePlayerFavoriteUseCase: RemovePlayerFavoriteUseCase by inject<RemovePlayerFavoriteUseCase>()
    private val getPlayersFavoriteUseCase: GetPlayersFavoriteUseCase by inject<GetPlayersFavoriteUseCase>()

    private val _uiModelIsFavorite: MutableStateFlow<ScreenStateUiModel<Boolean>> = MutableStateFlow(
        ScreenStateUiModel.Loading("")
    )
    private val _uiModelGetFavorites: MutableStateFlow<List<PlayerUi>> = MutableStateFlow(emptyList())

    val uiModelIsFavorite: StateFlow<ScreenStateUiModel<Boolean>> = _uiModelIsFavorite
    val uiModelGetFavorites: StateFlow<List<PlayerUi>> = _uiModelGetFavorites

    fun onEvent(favorite: PlayerFavoriteEvent) {
        when (favorite) {
            is PlayerFavoriteEvent.GetPlayerFavorite -> viewModelScope.launch {
                if(getPlayerFavoriteUseCase.invoke(favorite.playerId) == null) {
                    _uiModelIsFavorite.value = ScreenStateUiModel.Success(false)
                } else {
                    _uiModelIsFavorite.value = ScreenStateUiModel.Success(true)
                }
            }
            is PlayerFavoriteEvent.SavePlayerFavorite -> viewModelScope.launch {
                savePlayerFavoriteUseCase.invoke(favorite.player.toPlayer())
            }
            is PlayerFavoriteEvent.RemovePlayerFavorite -> viewModelScope.launch {
                removePlayerFavoriteUseCase.invoke(favorite.playerId)
            }
            is PlayerFavoriteEvent.GetAllPlayersFavorite -> viewModelScope.launch {
                _uiModelGetFavorites.value = getPlayersFavoriteUseCase.invoke().map {
                    it.toPlayerUi()
                }
            }
        }
    }

    sealed class PlayerFavoriteEvent{
        data class GetPlayerFavorite(val playerId: String): PlayerFavoriteEvent()
        data class RemovePlayerFavorite(val playerId: String): PlayerFavoriteEvent()
        data class SavePlayerFavorite(val player: PlayerUi): PlayerFavoriteEvent()
        data object GetAllPlayersFavorite: PlayerFavoriteEvent()
    }
}