package com.mscode.playercard.view.navigation

import androidx.compose.runtime.compositionLocalOf

interface PlayerCardController {
    fun navigate(screen: NavigationScreen)

    fun goBack()
}

internal val LocalNavController = compositionLocalOf<PlayerCardController> {
    DummyNavController()
}

private class DummyNavController : PlayerCardController {
    override fun navigate(screen: NavigationScreen) {
        throw IllegalStateException("You should provide a different instance of NavController")
    }

    override fun goBack() {
        throw IllegalStateException("You should provide a different instance of NavController")
    }
}