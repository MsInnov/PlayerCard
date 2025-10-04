package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.models.League
import com.mscode.playercard.domain.repository.LeaguesRepository

class GetLeaguesUseCase (
    private val leaguesRepository: LeaguesRepository
) {
    suspend fun invoke(): Result<List<League>> =  runCatching{
        leaguesRepository.getLeagues()
    }
}