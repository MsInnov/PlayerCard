package com.mscode.playercard.data.localdatasource.localvolatile

import com.mscode.playercard.data.localsource.PlayersLocalDataSource
import com.mscode.playercard.domain.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlayersLocalData: PlayersLocalDataSource {

    private val _players: MutableStateFlow<List<PlayersByTeamLocalData>> =  MutableStateFlow(emptyList())

    override val players: StateFlow<List<PlayersByTeamLocalData>>
        get() = _players

    override fun setPlayers(players: List<Player>, teamsId: String) {
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