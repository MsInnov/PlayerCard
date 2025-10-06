package com.mscode.playercard.view.fake

import com.mscode.playercard.domain.models.Team
import com.mscode.playercard.domain.repository.TeamsRepository

class FakeTeamsRepositorySuccess : TeamsRepository {

    override suspend fun getTeams(league: String): List<Team> = listOf(
        TestViewFactory.createTeam("1", "PSG"),
        TestViewFactory.createTeam("2", "Real Madrid"),
        TestViewFactory.createTeam("3", "Manchester City")
    )
}

class FakeTeamsRepositoryError : TeamsRepository {

    override suspend fun getTeams(league: String): List<Team> {
        throw RuntimeException("Network error for league=$league")
    }
}
