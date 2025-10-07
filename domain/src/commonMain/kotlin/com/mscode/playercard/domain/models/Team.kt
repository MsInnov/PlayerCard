package com.mscode.playercard.domain.models

data class Team(
    val teamId: String,
    val strTeam: String,
    val intFormedYear: String?,
    val strLeague: String?,
    val strStadium: String?,
    val strCountry: String?,
    val strBadge: String?
)
