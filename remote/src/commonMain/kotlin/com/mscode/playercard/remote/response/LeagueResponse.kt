package com.mscode.playercard.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LeagueResponse (
    val idLeague: String,
    val strLeague: String,
    val strSport: String
)