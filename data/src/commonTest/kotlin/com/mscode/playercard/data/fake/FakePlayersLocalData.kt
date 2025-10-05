package com.mscode.playercard.data.fake

import com.mscode.playercard.data.localDataSource.localVolatile.PlayersByTeamLocalData
import com.mscode.playercard.data.localSource.PlayersLocalDataSource
import com.mscode.playercard.domain.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakePlayersLocalData(
    initialPlayers: List<PlayersByTeamLocalData> = emptyList()
) : PlayersLocalDataSource {

    private val _players = MutableStateFlow(initialPlayers)
    override val players: StateFlow<List<PlayersByTeamLocalData>> = _players

    override fun setPlayers(players: List<Player>, teamsId: String) {
        setPlayersCallCount++
        lastTeamIdSet = teamsId

        val existingTeams = _players.value.toMutableList()
        val existingIndex = existingTeams.indexOfFirst { it.teamsId == teamsId }

        if (existingIndex >= 0) {
            existingTeams[existingIndex] = PlayersByTeamLocalData(teamsId, players)
        } else {
            existingTeams.add(PlayersByTeamLocalData(teamsId, players))
        }

        _players.value = existingTeams
    }

    var setPlayersCallCount = 0
        private set

    var lastTeamIdSet: String? = null
        private set

}