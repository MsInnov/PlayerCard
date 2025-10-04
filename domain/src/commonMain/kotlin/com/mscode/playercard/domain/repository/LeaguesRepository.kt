package com.mscode.playercard.domain.repository

import com.mscode.playercard.domain.models.League

interface LeaguesRepository {

    suspend fun getLeagues(): List<League>

}