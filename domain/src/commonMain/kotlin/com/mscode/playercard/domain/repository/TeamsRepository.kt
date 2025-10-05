package com.mscode.playercard.domain.repository

import com.mscode.playercard.domain.models.Team

interface TeamsRepository {

    suspend fun getTeams(league: String):  List<Team>

}