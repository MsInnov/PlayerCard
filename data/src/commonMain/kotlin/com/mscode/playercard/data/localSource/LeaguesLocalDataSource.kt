package com.mscode.playercard.data.localsource

import com.mscode.playercard.domain.models.League
import kotlinx.coroutines.flow.StateFlow

interface LeaguesLocalDataSource  {

    val leagues: StateFlow<List<League>>
    fun setLeagues(leagues: List<League>)

}