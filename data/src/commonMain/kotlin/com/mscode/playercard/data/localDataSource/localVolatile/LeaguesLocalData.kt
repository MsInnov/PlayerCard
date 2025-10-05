package com.mscode.playercard.data.localDataSource.localVolatile

import com.mscode.playercard.domain.models.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LeaguesLocalData {

    private val _leagues: MutableStateFlow<List<League>> =  MutableStateFlow(emptyList())
    val leagues: StateFlow<List<League>> =  _leagues

    fun setLeagues(leagues: List<League>) {
        _leagues.value = leagues
    }
}