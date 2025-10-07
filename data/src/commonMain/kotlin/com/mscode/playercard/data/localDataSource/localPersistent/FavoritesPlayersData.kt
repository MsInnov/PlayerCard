package com.mscode.playercard.data.localdatasource.localpersistent

import com.mscode.playercard.data.localsource.FavoritesPlayersLocalDataSource
import com.mscode.playercard.domain.models.Player
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FavoritesPlayersData(
    private val storage: KeyValueStorage
): FavoritesPlayersLocalDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override fun savePlayer(player: Player) {
        val data = json.encodeToString(player.toPlayerFavorite())
        storage.putString(player.idPlayer, data)
    }

    override fun getPlayer(playerId: String): Player? {
        val data = storage.getString(playerId, "")
        if (data.isEmpty()) return null
        return runCatching {
            json.decodeFromString<PlayerFavorite>(data).toPlayer()
        }.getOrNull()
    }

    override fun getPlayers(): List<Player> {
        val data = storage.getAll()

        return data.values
            .filterIsInstance<String>()
            .filter { it.isNotEmpty() }
            .mapNotNull { jsonString ->
                runCatching {
                    json.decodeFromString<PlayerFavorite>(jsonString).toPlayer()
                }.getOrNull()
            }
    }

    override fun clearPlayer(playerId: String) {
        storage.putString(playerId, "")
    }
}