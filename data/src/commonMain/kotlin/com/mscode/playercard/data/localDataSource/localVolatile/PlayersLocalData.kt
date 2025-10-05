package com.mscode.playercard.data.localDataSource.localVolatile

import com.mscode.playercard.domain.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlayersLocalData {

    private val _players: MutableStateFlow<List<PlayersByTeamLocalData>> =  MutableStateFlow(emptyList())
    val players: StateFlow<List<PlayersByTeamLocalData>> =  _players

    fun setPlayers(players: List<Player>, teamsId: String) {
        _players.update { playersByTeamLocalData ->
            val localDataUpdate = playersByTeamLocalData.toMutableList()
            localDataUpdate.add(
                PlayersByTeamLocalData(
                    teamsId = teamsId,
                    players = players
                )
            )
            localDataUpdate
        }
    }

}

data class PlayersByTeamLocalData(
    val teamsId: String,
    val players: List<Player>
)