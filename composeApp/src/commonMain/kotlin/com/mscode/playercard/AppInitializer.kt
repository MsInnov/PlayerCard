package com.mscode.playercard

import com.mscode.playercard.data.di.repositoryModule
import com.mscode.playercard.domain.di.useCaseModule
import com.mscode.playercard.remote.di.httpCommonModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

internal fun initCommonMobileApp(
    platformModulesInit: KoinApplication.() -> Unit,
) {
    startKoin {
        modules(
            listOf(
                httpCommonModule,
                repositoryModule,
                useCaseModule
            )
        )
        platformModulesInit()
    }
}