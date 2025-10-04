package com.mscode.playercard.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponse (
    val teams: List<TeamResponse>
)