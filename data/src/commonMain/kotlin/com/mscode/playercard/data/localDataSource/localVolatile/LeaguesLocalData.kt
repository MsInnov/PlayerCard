package com.mscode.playercard.data.localDataSource.localVolatile

import com.mscode.playercard.data.localSource.LeaguesLocalDataSource
import com.mscode.playercard.domain.models.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LeaguesLocalData: LeaguesLocalDataSource {

    private val _leagues: MutableStateFlow<List<League>> =  MutableStateFlow(emptyList())

    override val leagues: StateFlow<List<League>>
        get() = _leagues

    override fun setLeagues(leagues: List<League>) {
        _leagues.value = leagues
    }
}