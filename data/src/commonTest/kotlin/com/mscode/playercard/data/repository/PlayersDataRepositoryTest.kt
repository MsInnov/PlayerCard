package com.mscode.playercard.data.repository

import com.mscode.playercard.data.fake.FakePlayersLocalData
import com.mscode.playercard.data.fake.FakeRemotePlayerCard
import com.mscode.playercard.data.fake.TestDataFactory
import com.mscode.playercard.data.localdatasource.localvolatile.PlayersByTeamLocalData
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class PlayersDataRepositoryTest {

    private lateinit var repository: PlayersDataRepository
    private lateinit var fakeRemote: FakeRemotePlayerCard
    private lateinit var fakeLocal: FakePlayersLocalData

    private val testTeamId = "133604"
    private val anotherTeamId = "133605"

    @BeforeTest
    fun setup() {
        fakeRemote = FakeRemotePlayerCard()
        fakeLocal = FakePlayersLocalData()
        repository = PlayersDataRepository(fakeRemote, fakeLocal)
    }

    @Test
    fun `getPlayers should return local data when available for teamId`() = runTest {
        // Arrange
        val localPlayers = listOf(
            TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Messi"),
            TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Neymar")
        )
        val playersTeam = PlayersByTeamLocalData(teamsId = testTeamId, players = localPlayers)
        fakeLocal = FakePlayersLocalData(listOf(playersTeam))
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getPlayers(testTeamId)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Messi", result[0].strPlayer)
        assertEquals("Neymar", result[1].strPlayer)
        assertEquals(0, fakeRemote.getPlayersCallCount, "Remote should not be called")
    }

    @Test
    fun `getPlayers should fetch from remote when teamId not in local storage`() = runTest {
        // Arrange
        val remotePlayers = listOf(
            TestDataFactory.createPlayerResponse(idPlayer = "1", strPlayer = "Ronaldo"),
            TestDataFactory.createPlayerResponse(idPlayer = "2", strPlayer = "Benzema")
        )
        fakeRemote = FakeRemotePlayerCard(playersToReturn = remotePlayers)
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getPlayers(testTeamId)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Ronaldo", result[0].strPlayer)
        assertEquals("Benzema", result[1].strPlayer)
        assertEquals(1, fakeRemote.getPlayersCallCount, "Remote should be called once")
        assertEquals(testTeamId, fakeRemote.lastTeamIdUsed)
    }

    @Test
    fun `getPlayers should save remote data to local storage with correct teamId`() = runTest {
        // Arrange
        val remotePlayers = listOf(
            TestDataFactory.createPlayerResponse(idPlayer = "1", strPlayer = "Salah")
        )
        fakeRemote = FakeRemotePlayerCard(playersToReturn = remotePlayers)
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getPlayers(testTeamId)

        // Assert
        assertEquals(1, fakeLocal.setPlayersCallCount, "Local storage should be updated")
        assertEquals(testTeamId, fakeLocal.lastTeamIdSet)
        assertEquals(1, fakeLocal.players.value.size)
        assertEquals(testTeamId, fakeLocal.players.value[0].teamsId)
        assertEquals("Salah", fakeLocal.players.value[0].players[0].strPlayer)
    }

    @Test
    fun `getPlayers should fetch from remote when different teamId requested`() = runTest {
        // Arrange - Local data for team1
        val localPlayers = listOf(
            TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Messi")
        )
        val playersTeam = PlayersByTeamLocalData(teamsId = testTeamId, players = localPlayers)
        fakeLocal = FakePlayersLocalData(listOf(playersTeam))

        // Remote data for team2
        val remotePlayers = listOf(
            TestDataFactory.createPlayerResponse(idPlayer = "10", strPlayer = "Haaland")
        )
        fakeRemote = FakeRemotePlayerCard(playersToReturn = remotePlayers)
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act - Request different team
        val result = repository.getPlayers(anotherTeamId)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Haaland", result[0].strPlayer)
        assertEquals(1, fakeRemote.getPlayersCallCount, "Remote should be called for new team")
        assertEquals(anotherTeamId, fakeRemote.lastTeamIdUsed)
    }

    @Test
    fun `getPlayers should map all PlayerDto fields correctly`() = runTest {
        // Arrange
        val remotePlayer = TestDataFactory.createPlayerResponse(
            idPlayer = "123",
            strPlayer = "Test Player",
            strNationality = "France",
            strPosition = "Midfielder"
        )
        fakeRemote = FakeRemotePlayerCard(playersToReturn = listOf(remotePlayer))
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getPlayers(testTeamId)

        // Assert
        with(result[0]) {
            assertEquals("123", idPlayer)
            assertEquals("Test Player", strPlayer)
            assertEquals("France", strNationality)
            assertEquals("Midfielder", strPosition)
            assertEquals("PSG", strTeam)
            assertEquals("1987-06-24", dateBorn)
            assertEquals("30", strNumber)
            assertEquals("170 cm", strHeight)
            assertEquals("72 kg", strWeight)
            assertEquals("https://example.com/thumb.jpg", strThumb)
            assertEquals("https://example.com/cutout.png", strCutout)
        }
    }

    @Test
    fun `getPlayers should handle multiple teams in local storage`() = runTest {
        // Arrange
        val team1Players = listOf(
            TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Player1")
        )
        val team2Players = listOf(
            TestDataFactory.createPlayer(idPlayer = "10", strPlayer = "Player10")
        )
        val localData = listOf(
            PlayersByTeamLocalData(teamsId = testTeamId, players = team1Players),
            PlayersByTeamLocalData(teamsId = anotherTeamId, players = team2Players)
        )
        fakeLocal = FakePlayersLocalData(localData)
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result1 = repository.getPlayers(testTeamId)
        val result2 = repository.getPlayers(anotherTeamId)

        // Assert
        assertEquals("Player1", result1[0].strPlayer)
        assertEquals("Player10", result2[0].strPlayer)
        assertEquals(0, fakeRemote.getPlayersCallCount, "No remote calls needed")
    }

    @Test
    fun `getPlayers should handle empty player list from remote`() = runTest {
        // Arrange
        fakeRemote = FakeRemotePlayerCard(emptyList())
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getPlayers(testTeamId)

        // Assert
        assertTrue(result.isEmpty())
        assertEquals(1, fakeLocal.setPlayersCallCount, "Should still save empty list")
    }

    @Test
    fun `getPlayers should throw exception when remote fails`() = runTest {
        // Arrange
        fakeRemote = FakeRemotePlayerCard(shouldThrowError = true)
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act & Assert
        assertFailsWith<Exception> {
            repository.getPlayers(testTeamId)
        }
        assertEquals(0, fakeLocal.setPlayersCallCount, "Should not save on error")
    }

    @Test
    fun `getPlayers should handle large number of players`() = runTest {
        // Arrange
        val remotePlayers = (1..50).map { index ->
            TestDataFactory.createPlayerResponse(
                idPlayer = index.toString(),
                strPlayer = "Player$index"
            )
        }
        fakeRemote = FakeRemotePlayerCard(playersToReturn = remotePlayers)
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getPlayers(testTeamId)

        // Assert
        assertEquals(50, result.size)
        assertEquals("Player1", result[0].strPlayer)
        assertEquals("Player50", result[49].strPlayer)
    }

    @Test
    fun `getPlayers should preserve all nullable fields from DTO`() = runTest {
        // Arrange
        val playerWithNulls = TestDataFactory.createPlayerResponse(
            idPlayer = "1",
            strPlayer = "Minimal Player"
        ).copy(
            strTeam = null,
            strYoutube = null,
            strTwitter = null,
            strFacebook = null,
            strInstagram = null
        )
        fakeRemote = FakeRemotePlayerCard(playersToReturn = listOf(playerWithNulls))
        fakeLocal = FakePlayersLocalData(emptyList())
        repository = PlayersDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getPlayers(testTeamId)

        // Assert
        with(result[0]) {
            assertNull(strTeam)
            assertNull(strYoutube)
            assertNull(strTwitter)
            assertNull(strFacebook)
            assertNull(strInstagram)
        }
    }
}