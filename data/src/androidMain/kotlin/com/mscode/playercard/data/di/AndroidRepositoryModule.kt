package com.mscode.playercard.data.di

import com.mscode.playercard.data.localdatasource.localpersistent.KeyValueStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidRepositoryModule = module {
    single<KeyValueStorage> { KeyValueStorage(androidContext()) }
}