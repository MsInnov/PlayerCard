package com.mscode.playercard.domain.repository

import com.mscode.playercard.domain.models.Teams

interface TeamsRepository {

    suspend fun getTeams(league: String):  List<Teams>

}