package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localsource.LeaguesLocalDataSource
import com.mscode.playercard.domain.models.League
import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.remote.remotesource.PlayerCardRemoteDataSource

class LeaguesDataRepository (
    private val remotePlayerCard: PlayerCardRemoteDataSource,
    private val leaguesLocalData: LeaguesLocalDataSource
): LeaguesRepository {

    override suspend fun getLeagues(): List<League> =
        leaguesLocalData.leagues.value.ifEmpty {
            val leagues = remotePlayerCard.getLeagues().leagues.map { league ->
                League(
                    league = league.strLeague,
                    sport = league.strSport,
                    idLeague = league.idLeague
                )
            }
            leaguesLocalData.setLeagues(leagues)
            leagues
        }
}