package com.mscode.playercard.domain.fake

import com.mscode.playercard.domain.models.Team
import com.mscode.playercard.domain.repository.TeamsRepository

class FakeTeamsRepositorySuccess : TeamsRepository {

    override suspend fun getTeams(league: String): List<Team> = listOf(
        TestDomainFactory.createTeam("1", "PSG"),
        TestDomainFactory.createTeam("2", "Real Madrid"),
        TestDomainFactory.createTeam("3", "Manchester City")
    )
}

class FakeTeamsRepositoryError : TeamsRepository {

    override suspend fun getTeams(league: String): List<Team> {
        throw RuntimeException("Network error for league=$league")
    }
}
