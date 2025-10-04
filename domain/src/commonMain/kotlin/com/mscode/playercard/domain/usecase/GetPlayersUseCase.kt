package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersRepository

class GetPlayersUseCase(
    private val playersRepository: PlayersRepository
) {
    suspend fun invoke(teamId: String): Result<List<Player>> =  runCatching{
        playersRepository.getPlayers(teamId)
    }
}