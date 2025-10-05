package com.mscode.playercard.data.fake

import com.mscode.playercard.data.localSource.LeaguesLocalDataSource
import com.mscode.playercard.domain.models.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeLeaguesLocalData(
    initialLeagues: List<League> = emptyList()
) : LeaguesLocalDataSource {

    private val _leagues = MutableStateFlow(initialLeagues)
    override val leagues: StateFlow<List<League>> = _leagues

    var setLeaguesCallCount = 0
        private set

    override fun setLeagues(leagues: List<League>) {
        setLeaguesCallCount++
        _leagues.value = leagues
    }
}