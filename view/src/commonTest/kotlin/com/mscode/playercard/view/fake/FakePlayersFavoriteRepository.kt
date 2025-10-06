package com.mscode.playercard.view.fake

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class FakePlayersFavoriteRepositorySuccess : PlayersFavoriteRepository {
    private val favorites = mutableListOf(
        TestViewFactory.createPlayer("1", "Messi"),
        TestViewFactory.createPlayer("2", "Mbappé"),
        TestViewFactory.createPlayer("3", "Haaland")
    )

    fun reloadFavorites() {
        val favoriteTmp = favorites
        favorites.removeAll(favoriteTmp)
        favorites.add(
            TestViewFactory.createPlayer("1", "Messi")
        )
        favorites.add(
            TestViewFactory.createPlayer("2", "Mbappé")
        )
        favorites.add(
            TestViewFactory.createPlayer("3", "Haaland")
        )
    }

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
        TestViewFactory.createPlayer("1", "Messi"),
        TestViewFactory.createPlayer("2", "Mbappé")
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