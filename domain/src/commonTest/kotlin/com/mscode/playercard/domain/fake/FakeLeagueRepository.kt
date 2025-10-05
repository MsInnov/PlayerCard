package com.mscode.playercard.domain.fake

import com.mscode.playercard.domain.models.League
import com.mscode.playercard.domain.repository.LeaguesRepository

class FakeLeaguesRepositorySuccess : LeaguesRepository {
    override suspend fun getLeagues(): List<League> = listOf(
        League("1", "Premier League", "Soccer"),
        League("2", "NBA", "Basketball")
    )
}

class FakeLeaguesRepositoryError : LeaguesRepository {
    override suspend fun getLeagues(): List<League> {
        throw RuntimeException("Network error")
    }
}