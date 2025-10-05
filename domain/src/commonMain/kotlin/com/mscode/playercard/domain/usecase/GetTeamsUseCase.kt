package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.models.Team
import com.mscode.playercard.domain.repository.TeamsRepository

class GetTeamsUseCase (
    private val teamsRepository: TeamsRepository
) {
    suspend fun invoke(league: String): Result<List<Team>> =  runCatching{
        teamsRepository.getTeams(league)
    }
}