package com.mscode.playercard.data.repository

import com.mscode.playercard.data.localDataSource.localVolatile.LeaguesLocalData
import com.mscode.playercard.domain.models.League
import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.remote.RemotePlayerCard

class LeaguesDataRepository (
    private val remotePlayerCard: RemotePlayerCard,
    private val leaguesLocalData: LeaguesLocalData
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