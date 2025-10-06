package com.mscode.playercard.view.fake

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersRepository

class FakePlayersRepositorySuccess : PlayersRepository {
    override suspend fun getPlayers(teamId: String): List<Player> = listOf(
        TestViewFactory.createPlayer("1", "Messi"),
        TestViewFactory.createPlayer("2", "Mbappé"),
        TestViewFactory.createPlayer("3", "Haaland")
    )
}

class FakePlayersRepositoryError : PlayersRepository {
    override suspend fun getPlayers(teamId: String): List<Player> {
        throw RuntimeException("Network error for teamId=$teamId")
    }
}