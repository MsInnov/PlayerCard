package com.mscode.playercard.data.localSource

import com.mscode.playercard.data.localDataSource.localVolatile.PlayersByTeamLocalData
import com.mscode.playercard.domain.models.Player
import kotlinx.coroutines.flow.StateFlow

interface PlayersLocalDataSource {

    val players: StateFlow<List<PlayersByTeamLocalData>>
    fun setPlayers(players: List<Player>, teamsId: String)

}