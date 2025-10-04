package com.mscode.playercard

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = {
        initIosPlayerCardApplication()
    }
){
    App()
}

