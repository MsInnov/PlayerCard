package com.mscode.playercard.remote.di

import com.mscode.playercard.remote.http.HttpClientFactory
import com.mscode.playercard.remote.RemotePlayerCard
import com.mscode.playercard.remote.http.HttpPlayerCardFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val httpCommonModule = module {
    includes(remotePlatformModule())
    factory { HttpClientFactory(getOrNull(), getOrNull()) }

    singleOf(::HttpPlayerCardFactory)
    singleOf(::RemotePlayerCard)
}
