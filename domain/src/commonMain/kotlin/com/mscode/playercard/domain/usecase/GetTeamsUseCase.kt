package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.models.Teams
import com.mscode.playercard.domain.repository.TeamsRepository

class GetTeamsUseCase (
    private val teamsRepository: TeamsRepository
) {
    suspend fun invoke(league: String): Result<List<Teams>> =  runCatching{
        teamsRepository.getTeams(league)
    }
}