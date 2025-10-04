package com.mscode.playercard.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LeaguesResponse (
    val leagues: List<LeagueResponse>
)