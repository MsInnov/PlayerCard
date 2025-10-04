package com.mscode.playercard

import org.koin.android.ext.koin.androidContext

internal fun initAndroidPlayerCardApplication(application: PlayerCardAndroidApplication) {
    initCommonMobileApp {
        androidContext(application)
    }
}
