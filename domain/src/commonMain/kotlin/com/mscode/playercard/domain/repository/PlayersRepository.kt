package com.mscode.playercard.domain.repository

import com.mscode.playercard.domain.models.Player

interface PlayersRepository {

    suspend fun getPlayers(teamId: String): List<Player>

}