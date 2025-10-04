package com.mscode.playercard.view.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel(), PlayerCardController {

    private val navigationSharedFlow = MutableSharedFlow<NavigationScreen?>()
    val navigationFlow: Flow<NavigationScreen?>
        get() = navigationSharedFlow

    override fun navigate(screen: NavigationScreen) {
        viewModelScope.launch {
            navigationSharedFlow.emit(screen)
        }
    }

    override fun goBack() {
        viewModelScope.launch {
            navigationSharedFlow.emit(null)
        }
    }
}