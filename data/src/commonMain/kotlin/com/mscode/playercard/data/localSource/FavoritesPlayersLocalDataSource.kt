package com.mscode.playercard.data.localsource

import com.mscode.playercard.domain.models.Player

interface FavoritesPlayersLocalDataSource {
    fun savePlayer(player: Player)
    fun getPlayer(playerId: String): Player?
    fun getPlayers(): List<Player>
    fun clearPlayer(playerId: String)
}