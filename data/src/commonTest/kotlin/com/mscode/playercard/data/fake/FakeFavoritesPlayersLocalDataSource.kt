package com.mscode.playercard.data.fake

import com.mscode.playercard.data.localSource.FavoritesPlayersLocalDataSource
import com.mscode.playercard.domain.models.Player

class FakeFavoritesPlayersLocalDataSource(
    initialPlayers: List<Player> = emptyList()
) : FavoritesPlayersLocalDataSource {

    private val players = initialPlayers.toMutableList()

    var getPlayersCallCount = 0
        private set

    var getPlayerCallCount = 0
        private set

    var savePlayerCallCount = 0
        private set

    var clearPlayerCallCount = 0
        private set

    var lastPlayerIdQueried: String? = null
        private set

    var lastPlayerSaved: Player? = null
        private set

    var lastPlayerIdCleared: String? = null
        private set

    override fun getPlayers(): List<Player> {
        getPlayersCallCount++
        return players.toList()
    }

    override fun getPlayer(playerId: String): Player? {
        getPlayerCallCount++
        lastPlayerIdQueried = playerId
        return players.firstOrNull { it.idPlayer == playerId }
    }

    override fun savePlayer(player: Player) {
        savePlayerCallCount++
        lastPlayerSaved = player

        // Si le joueur existe déjà, on le remplace, sinon on l'ajoute
        val existingIndex = players.indexOfFirst { it.idPlayer == player.idPlayer }
        if (existingIndex >= 0) {
            players[existingIndex] = player
        } else {
            players.add(player)
        }
    }

    override fun clearPlayer(playerId: String) {
        clearPlayerCallCount++
        lastPlayerIdCleared = playerId
        players.removeAll { it.idPlayer == playerId }
    }

    // Helper pour les tests
    fun reset() {
        players.clear()
        getPlayersCallCount = 0
        getPlayerCallCount = 0
        savePlayerCallCount = 0
        clearPlayerCallCount = 0
        lastPlayerIdQueried = null
        lastPlayerSaved = null
        lastPlayerIdCleared = null
    }
}