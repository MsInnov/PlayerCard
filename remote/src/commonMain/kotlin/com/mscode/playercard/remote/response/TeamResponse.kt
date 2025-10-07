package com.mscode.playercard.remote.response

import kotlinx.serialization.Serializable

@Serializable
class TeamResponse (
    val idTeam: String,
    val strTeam: String,
    val intFormedYear: String?,
    val strLeague: String?,
    val strStadium: String?,
    val strCountry: String?,
    val strBadge: String?
)