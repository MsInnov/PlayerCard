package com.mscode.playercard

import android.app.Application

class PlayerCardAndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAndroidPlayerCardApplication(this)
    }
}

