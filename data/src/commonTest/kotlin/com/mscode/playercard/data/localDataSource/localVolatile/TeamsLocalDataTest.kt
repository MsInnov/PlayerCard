package com.mscode.playercard.data.localdatasource.localvolatile

import com.mscode.playercard.data.fake.TestDataFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class TeamsLocalDataTest {

    @Test
    fun `initial teams is empty`() = runTest {
        val localData = TeamsLocalData()
        val current = localData.teams.value
        assertTrue(current.isEmpty(), "Expected teams to be empty initially")
    }

    @Test
    fun `setTeams adds teams correctly`() = runTest {
        val localData = TeamsLocalData()
        val teamsToAdd = listOf(TestDataFactory.createTeam(teamId = "1", strTeam = "PSG"), TestDataFactory.createTeam(teamId = "1", strTeam = "AUXERRE"))

        localData.setTeams(teamsToAdd, "league1")

        val current = localData.teams.value
        assertEquals(1, current.size)
        assertEquals("league1", current.first().leagueId)
        assertEquals(2, current.first().teams.size)
        assertEquals("PSG", current.first().teams[0].strTeam)
    }

    @Test
    fun `setTeams preserves previous data`() = runTest {
        val localData = TeamsLocalData()

        localData.setTeams(listOf(TestDataFactory.createTeam(teamId = "1", strTeam = "Team A")), "league1")
        localData.setTeams(listOf(TestDataFactory.createTeam(teamId = "2", strTeam = "Team B")), "league2")

        val current = localData.teams.value
        assertEquals(2, current.size)

        assertEquals("league1", current[0].leagueId)
        assertEquals("Team A", current[0].teams[0].strTeam)

        assertEquals("league2", current[1].leagueId)
        assertEquals("Team B", current[1].teams[0].strTeam)
    }
}