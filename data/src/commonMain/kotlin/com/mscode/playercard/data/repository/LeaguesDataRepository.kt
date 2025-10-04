package com.mscode.playercard.data.repository

import com.mscode.playercard.domain.models.League
import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.remote.RemotePlayerCard

class LeaguesDataRepository (
    private val remotePlayerCard: RemotePlayerCard
): LeaguesRepository {

    override suspend fun getLeagues(): List<League> =
        remotePlayerCard.getLeagues().leagues.map { league ->
            League(
                league = league.strLeague,
                sport = league.strSport,
                idLeague = league.idLeague
            )
        }
}