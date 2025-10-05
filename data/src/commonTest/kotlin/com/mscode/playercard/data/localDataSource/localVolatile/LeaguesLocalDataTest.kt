package com.mscode.playercard.data.localDataSource.localVolatile

import com.mscode.playercard.domain.models.League
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LeaguesLocalDataTest {

    @Test
    fun `initial leagues is empty`() = runTest {
        val localData = LeaguesLocalData()
        val current = localData.leagues.value

        assertTrue(current.isEmpty(), "Expected leagues to be empty initially")
    }

    @Test
    fun `setLeagues updates leagues correctly`() = runTest {
        val localData = LeaguesLocalData()
        val leaguesToAdd = listOf(
            League("1", "Premier League", "Soccer"),
            League("2", "NBA", "Basketball")
        )

        localData.setLeagues(leaguesToAdd)

        val current = localData.leagues.value
        assertEquals(expected = 2, actual = current.size)
        assertEquals(expected = "Premier League", current[0].league)
        assertEquals(expected = "NBA", current[1].league)
    }

    @Test
    fun `setLeagues replaces existing leagues`() = runTest {
        val localData = LeaguesLocalData()

        val firstSet = listOf(League("1", "Ligue 1", "Soccer"))
        val secondSet = listOf(League("2", "Serie A", "Soccer"))

        localData.setLeagues(firstSet)
        localData.setLeagues(secondSet)

        val current = localData.leagues.value
        assertEquals(1, current.size)
        assertEquals("Serie A", current.first().league)
    }
}