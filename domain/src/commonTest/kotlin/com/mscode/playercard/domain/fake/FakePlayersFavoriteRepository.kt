package com.mscode.playercard.domain.fake

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class FakePlayersFavoriteRepositorySuccess : PlayersFavoriteRepository {
    private val favorites = mutableListOf(
        TestDomainFactory.createPlayer("1", "Messi"),
        TestDomainFactory.createPlayer("2", "Mbappé"),
        TestDomainFactory.createPlayer("3", "Haaland")
    )

    override fun getPlayersFavorite(): List<Player> = favorites

    override fun getPlayerFavorite(playerId: String): Player? {
        return favorites.find { it.idPlayer == playerId }
    }

    override fun savePlayerFavorite(player: Player) {
        favorites.add(player)
    }

    override fun removePlayerFavorite(playerId: String) {
        val favorite = favorites.first{it.idPlayer == playerId}
        favorites.remove(favorite)
    }
}

class FakePlayersFavoriteRepositoryEmpty :PlayersFavoriteRepository {
    private val favorites = listOf(
        TestDomainFactory.createPlayer("1", "Messi"),
        TestDomainFactory.createPlayer("2", "Mbappé")
    )

    override fun getPlayersFavorite(): List<Player> = emptyList()

    override fun getPlayerFavorite(playerId: String): Player? = null

    override fun savePlayerFavorite(player: Player) {
        TODO("Not yet implemented")
    }

    override fun removePlayerFavorite(playerId: String) {
        val favorite = favorites.first{it.idPlayer == playerId}
        favorites.toMutableList().remove(favorite)
    }
}