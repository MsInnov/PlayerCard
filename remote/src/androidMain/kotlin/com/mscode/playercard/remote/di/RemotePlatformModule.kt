package com.mscode.playercard.remote.di

import com.mscode.playercard.remote.AndroidBrotliEncoder
import com.mscode.playercard.remote.http.BrotliEncoder
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual fun remotePlatformModule() = module {
    factoryOf(::AndroidBrotliEncoder) { bind<BrotliEncoder>() }
}