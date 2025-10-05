package com.mscode.playercard.domain.repository

import com.mscode.playercard.domain.models.Player

interface PlayersFavoriteRepository {

    fun getPlayersFavorite(): List<Player>
    fun getPlayerFavorite(playerId: String): Player?
    fun savePlayerFavorite(player: Player)
    fun removePlayerFavorite(playerId: String)

}