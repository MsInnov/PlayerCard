package com.mscode.playercard.data.localsource

import com.mscode.playercard.data.localdatasource.localvolatile.PlayersByTeamLocalData
import com.mscode.playercard.domain.models.Player
import kotlinx.coroutines.flow.StateFlow

interface PlayersLocalDataSource {

    val players: StateFlow<List<PlayersByTeamLocalData>>
    fun setPlayers(players: List<Player>, teamsId: String)

}