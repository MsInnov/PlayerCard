package com.mscode.playercard

import com.mscode.playercard.data.di.iosRepositoryModule

internal fun initIosPlayerCardApplication() {
    initCommonMobileApp {
        modules(iosRepositoryModule)
    }
}
