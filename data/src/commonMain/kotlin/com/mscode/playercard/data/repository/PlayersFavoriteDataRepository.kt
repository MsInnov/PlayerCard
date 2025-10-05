package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localDataSource.localPersistent.FavoritesPlayersData
import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class PlayersFavoriteDataRepository(
    private val favoritesPlayersData: FavoritesPlayersData
): PlayersFavoriteRepository {

    override fun getPlayersFavorite(): List<Player> =
        favoritesPlayersData.getPlayers()


    override fun getPlayerFavorite(playerId: String): Player? =
        favoritesPlayersData.getPlayer(playerId)


    override fun savePlayerFavorite(player: Player) {
        favoritesPlayersData.savePlayer(player)
    }

    override fun removePlayerFavorite(playerId: String) {
        favoritesPlayersData.clearPlayer(playerId)
    }

}