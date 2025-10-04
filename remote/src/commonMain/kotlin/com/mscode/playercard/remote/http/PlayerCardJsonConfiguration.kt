package com.mscode.playercard.remote.http

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

@Suppress("LongMethod")
fun createPlayerCardJsonConfiguration(): Json = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
    isLenient = true

    serializersModule = SerializersModule {}
}
