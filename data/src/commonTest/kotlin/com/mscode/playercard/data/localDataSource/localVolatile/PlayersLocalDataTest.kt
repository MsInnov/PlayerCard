package com.mscode.playercard.data.localDataSource.localVolatile

import com.mscode.playercard.data.fake.TestDataFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlayersLocalDataTest {

    @Test
    fun `initial players is empty`() = runTest {
        val localData = PlayersLocalData()
        assertTrue(localData.players.value.isEmpty(), "Expected players to be empty initially")
    }

    @Test
    fun `setPlayers adds players correctly`() = runTest {
        val localData = PlayersLocalData()
        val playersToAdd = listOf(
            TestDataFactory.createPlayer("1", "Player A"),
            TestDataFactory.createPlayer("2", "Player B")
        )

        localData.setPlayers(playersToAdd, "team1")

        val current = localData.players.value
        assertEquals(1, current.size)
        assertEquals("team1", current.first().teamsId)
        assertEquals(2, current.first().players.size)
        assertEquals("Lionel Messi", current.first().players[0].strPlayer)
    }

    @Test
    fun `setPlayers preserves previous data`() = runTest {
        val localData = PlayersLocalData()

        localData.setPlayers(
            listOf(
                TestDataFactory.createPlayer("1", "Player A")
            ),
            "team1"
        )
        localData.setPlayers(
            listOf(
                TestDataFactory.createPlayer("2", "Player B")
            ),
            "team2"
        )

        val current = localData.players.value
        assertEquals(2, current.size)

        assertEquals("team1", current[0].teamsId)
        assertEquals("Lionel Messi", current[0].players[0].strPlayer)

        assertEquals("team2", current[1].teamsId)
        assertEquals("Lionel Messi", current[1].players[0].strPlayer)
    }
}