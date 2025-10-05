package com.mscode.playercard.domain.fake

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersRepository

class FakePlayersRepositorySuccess : PlayersRepository {
    override suspend fun getPlayers(teamId: String): List<Player> = listOf(
        TestDomainFactory.createPlayer("1", "Messi"),
        TestDomainFactory.createPlayer("2", "Mbappé"),
        TestDomainFactory.createPlayer("3", "Haaland")
    )
}

class FakePlayersRepositoryError : PlayersRepository {
    override suspend fun getPlayers(teamId: String): List<Player> {
        throw RuntimeException("Network error for teamId=$teamId")
    }
}