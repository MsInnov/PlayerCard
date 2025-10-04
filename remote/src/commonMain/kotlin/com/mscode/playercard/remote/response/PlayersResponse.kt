package com.mscode.playercard.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponse (
    val player: List<PlayerResponse>
)