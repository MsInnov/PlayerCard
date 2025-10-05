package com.mscode.playercard.data.repository

import com.mscode.playercard.data.fake.FakeLeaguesLocalData
import com.mscode.playercard.data.fake.FakeRemotePlayerCard
import com.mscode.playercard.domain.models.League
import com.mscode.playercard.remote.response.LeagueResponse
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class LeaguesDataRepositoryTest {

    private lateinit var repository: LeaguesDataRepository
    private lateinit var fakeRemote: FakeRemotePlayerCard
    private lateinit var fakeLocal: FakeLeaguesLocalData

    @BeforeTest
    fun setup() {
        fakeRemote = FakeRemotePlayerCard()
        fakeLocal = FakeLeaguesLocalData()
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)
    }

    @Test
    fun `getLeagues should return local data when available`() = runTest {
        // Arrange
        val localLeagues = listOf(
            League(league = "Ligue 1", sport = "Soccer", idLeague = "1"),
            League(league = "La Liga", sport = "Soccer", idLeague = "2")
        )
        fakeLocal = FakeLeaguesLocalData(localLeagues)
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getLeagues()

        // Assert
        assertEquals(localLeagues, result)
        assertEquals(0, fakeRemote.getLeaguesCallCount, "Remote should not be called")
    }

    @Test
    fun `getLeagues should fetch from remote when local is empty`() = runTest {
        // Arrange
        val remoteLeagues = listOf(
            LeagueResponse(idLeague = "1", strLeague = "Premier League", strSport = "Soccer"),
            LeagueResponse(idLeague = "2", strLeague = "NBA", strSport = "Basketball")
        )
        fakeRemote = FakeRemotePlayerCard(leaguesToReturn = remoteLeagues)
        fakeLocal = FakeLeaguesLocalData(emptyList())
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getLeagues()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Premier League", result[0].league)
        assertEquals("Soccer", result[0].sport)
        assertEquals("1", result[0].idLeague)
        assertEquals(1, fakeRemote.getLeaguesCallCount, "Remote should be called once")
    }

    @Test
    fun `getLeagues should save remote data to local storage`() = runTest {
        // Arrange
        val remoteLeagues = listOf(
            LeagueResponse(idLeague = "1", strLeague = "Serie A", strSport = "Soccer")
        )
        fakeRemote = FakeRemotePlayerCard(leaguesToReturn = remoteLeagues)
        fakeLocal = FakeLeaguesLocalData(emptyList())
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getLeagues()

        // Assert
        assertEquals(1, fakeLocal.setLeaguesCallCount, "Local storage should be updated")
        assertEquals(1, fakeLocal.leagues.value.size)
        assertEquals("Serie A", fakeLocal.leagues.value[0].league)
    }

    @Test
    fun `getLeagues should map DTO fields correctly`() = runTest {
        // Arrange
        val remoteLeagues = listOf(
            LeagueResponse(
                idLeague = "123",
                strLeague = "Bundesliga",
                strSport = "Football"
            )
        )
        fakeRemote = FakeRemotePlayerCard(leaguesToReturn = remoteLeagues)
        fakeLocal = FakeLeaguesLocalData(emptyList())
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getLeagues()

        // Assert
        with(result[0]) {
            assertEquals("Bundesliga", league)
            assertEquals("Football", sport)
            assertEquals("123", idLeague)
        }
    }

    @Test
    fun `getLeagues should handle multiple leagues`() = runTest {
        // Arrange
        val remoteLeagues = listOf(
            LeagueResponse("1", "League 1", "Sport 1"),
            LeagueResponse("2", "League 2", "Sport 2"),
            LeagueResponse("3", "League 3", "Sport 3")
        )
        fakeRemote = FakeRemotePlayerCard(leaguesToReturn = remoteLeagues)
        fakeLocal = FakeLeaguesLocalData(emptyList())
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getLeagues()

        // Assert
        assertEquals(3, result.size)
        assertEquals("League 1", result[0].league)
        assertEquals("League 3", result[2].league)
    }

    @Test
    fun `getLeagues should throw exception when remote fails`() = runTest {
        // Arrange
        fakeRemote = FakeRemotePlayerCard(shouldThrowError = true)
        fakeLocal = FakeLeaguesLocalData(emptyList())
        repository = LeaguesDataRepository(fakeRemote, fakeLocal)

        // Act & Assert
        assertFailsWith<Exception> {
            repository.getLeagues()
        }
    }
}