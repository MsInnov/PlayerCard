package com.mscode.playercard

import com.mscode.playercard.data.di.androidRepositoryModule
import org.koin.android.ext.koin.androidContext

internal fun initAndroidPlayerCardApplication(application: PlayerCardAndroidApplication) {
    initCommonMobileApp {
        androidContext(application)
        modules(androidRepositoryModule)
    }
}
