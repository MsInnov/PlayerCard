package com.mscode.playercard.data.di

import com.mscode.playercard.data.localDataSource.localPersistent.KeyValueStorage
import org.koin.dsl.module

val iosRepositoryModule = module {
    single<KeyValueStorage> { KeyValueStorage(null) }
}
