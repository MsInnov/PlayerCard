package com.mscode.playercard.remote.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun remotePlatformModule() = module {
    single<HttpClientEngine> { Darwin.create() }
}