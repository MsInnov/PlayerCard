package com.mscode.playercard.view.viewmodel

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.mp.KoinPlatform.stopKoin

fun initKoin(vararg modules: Module) {
    stopKoin()
    startKoin {
        modules(*modules)
    }
}