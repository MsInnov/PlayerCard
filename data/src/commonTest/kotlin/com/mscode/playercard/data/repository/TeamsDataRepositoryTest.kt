package com.mscode.playercard.data.repository

import com.mscode.playercard.data.fake.FakeRemotePlayerCard
import com.mscode.playercard.data.fake.FakeTeamsLocalData
import com.mscode.playercard.data.fake.TestDataFactory
import com.mscode.playercard.data.localdatasource.localvolatile.TeamsByLeagueLocalData
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class TeamsDataRepositoryTest {

    private lateinit var repository: TeamsDataRepository
    private lateinit var fakeRemote: FakeRemotePlayerCard
    private lateinit var fakeLocal: FakeTeamsLocalData

    private val testLeague = "French Ligue 1"
    private val anotherLeague = "English Premier League"

    @BeforeTest
    fun setup() {
        fakeRemote = FakeRemotePlayerCard()
        fakeLocal = FakeTeamsLocalData()
        repository = TeamsDataRepository(fakeRemote, fakeLocal)
    }

    // ========== Local Data Tests ==========

    @Test
    fun `getTeams should return local data when available for league`() = runTest {
        // Arrange
        val localTeams = listOf(
            TestDataFactory.createTeam(teamId = "1", strTeam = "PSG"),
            TestDataFactory.createTeam(teamId = "2", strTeam = "Lyon")
        )
        val teamsLeague = TeamsByLeagueLocalData(leagueId = testLeague, teams = localTeams)
        fakeLocal = FakeTeamsLocalData(listOf(teamsLeague))
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getTeams(testLeague)

        // Assert
        assertEquals(2, result.size)
        assertEquals("PSG", result[0].strTeam)
        assertEquals("Lyon", result[1].strTeam)
        assertEquals(0, fakeRemote.getTeamsCallCount, "Remote should not be called")
    }

    @Test
    fun `getTeams should return local data without modifying league name`() = runTest {
        // Arrange
        val leagueWithSpaces = "English Premier League"
        val localTeams = listOf(
            TestDataFactory.createTeam(teamId = "1", strTeam = "Manchester United")
        )
        val teamsLeague = TeamsByLeagueLocalData(leagueId = leagueWithSpaces, teams = localTeams)
        fakeLocal = FakeTeamsLocalData(listOf(teamsLeague))
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getTeams(leagueWithSpaces)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Manchester United", result[0].strTeam)
        assertEquals(0, fakeRemote.getTeamsCallCount)
    }

    // ========== Remote Fetch Tests ==========

    @Test
    fun `getTeams should fetch from remote when league not in local storage`() = runTest {
        // Arrange
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "Real Madrid"),
            TestDataFactory.createTeamResponse(idTeam = "2", strTeam = "Barcelona")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getTeams(testLeague)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Real Madrid", result[0].strTeam)
        assertEquals("Barcelona", result[1].strTeam)
        assertEquals(1, fakeRemote.getTeamsCallCount, "Remote should be called once")
    }

    @Test
    fun `getTeams should replace spaces with underscores when calling remote`() = runTest {
        // Arrange
        val leagueWithSpaces = "English Premier League"
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "Chelsea")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getTeams(leagueWithSpaces)

        // Assert
        assertEquals("English_Premier_League", fakeRemote.lastLeagueUsed)
        assertEquals(1, fakeRemote.getTeamsCallCount)
    }

    @Test
    fun `getTeams should handle league with multiple spaces correctly`() = runTest {
        // Arrange
        val leagueWithManySpaces = "Major League Soccer USA"
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "LA Galaxy")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getTeams(leagueWithManySpaces)

        // Assert
        assertEquals("Major_League_Soccer_USA", fakeRemote.lastLeagueUsed)
    }

    @Test
    fun `getTeams should handle league without spaces correctly`() = runTest {
        // Arrange
        val leagueNoSpaces = "Bundesliga"
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "Bayern Munich")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getTeams(leagueNoSpaces)

        // Assert
        assertEquals("Bundesliga", fakeRemote.lastLeagueUsed)
    }

    // ========== Local Storage Tests ==========

    @Test
    fun `getTeams should save remote data to local storage with original league name`() = runTest {
        // Arrange
        val leagueWithSpaces = "Spanish La Liga"
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "Atletico Madrid")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getTeams(leagueWithSpaces)

        // Assert
        assertEquals(1, fakeLocal.setTeamsCallCount, "Local storage should be updated")
        assertEquals(leagueWithSpaces, fakeLocal.lastLeagueIdSet, "Should use original league name")
        assertEquals(1, fakeLocal.teams.value.size)
        assertEquals(leagueWithSpaces, fakeLocal.teams.value[0].leagueId)
        assertEquals("Atletico Madrid", fakeLocal.teams.value[0].teams[0].strTeam)
    }

    @Test
    fun `getTeams should fetch from remote when different league requested`() = runTest {
        // Arrange - Local data for league1
        val localTeams = listOf(
            TestDataFactory.createTeam(teamId = "1", strTeam = "PSG")
        )
        val teamsLeague = TeamsByLeagueLocalData(leagueId = testLeague, teams = localTeams)
        fakeLocal = FakeTeamsLocalData(listOf(teamsLeague))

        // Remote data for league2
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "10", strTeam = "Manchester City")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act - Request different league
        val result = repository.getTeams(anotherLeague)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Manchester City", result[0].strTeam)
        assertEquals(1, fakeRemote.getTeamsCallCount, "Remote should be called for new league")
    }

    // ========== Mapping Tests ==========

    @Test
    fun `getTeams should map all TeamDto fields correctly`() = runTest {
        // Arrange
        val remoteTeam = TestDataFactory.createTeamResponse(
            idTeam = "123",
            strTeam = "Test Team",
            strLeague = "Test League",
            strStadium = "Test Stadium",
            strCountry = "Test Country"
        )
        fakeRemote = FakeRemotePlayerCard(listOf(remoteTeam))
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getTeams(testLeague)

        // Assert
        with(result[0]) {
            assertEquals("123", teamId)
            assertEquals("Test Team", strTeam)
            assertEquals("1970", intFormedYear)
            assertEquals("Test League", strLeague)
            assertEquals("Test Stadium", strStadium)
            assertEquals("Test Country", strCountry)
            assertEquals("https://example.com/badge.png", strBadge)
        }
    }

    // ========== Multiple Leagues Tests ==========

    @Test
    fun `getTeams should handle multiple leagues in local storage`() = runTest {
        // Arrange
        val league1Teams = listOf(
            TestDataFactory.createTeam(teamId = "1", strTeam = "Team1")
        )
        val league2Teams = listOf(
            TestDataFactory.createTeam(teamId = "10", strTeam = "Team10")
        )
        val localData = listOf(
            TeamsByLeagueLocalData(leagueId = testLeague, teams = league1Teams),
            TeamsByLeagueLocalData(leagueId = anotherLeague, teams = league2Teams)
        )
        fakeLocal = FakeTeamsLocalData(localData)
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result1 = repository.getTeams(testLeague)
        val result2 = repository.getTeams(anotherLeague)

        // Assert
        assertEquals("Team1", result1[0].strTeam)
        assertEquals("Team10", result2[0].strTeam)
        assertEquals(0, fakeRemote.getTeamsCallCount, "No remote calls needed")
    }

    // ========== Edge Cases Tests ==========

    @Test
    fun `getTeams should handle empty team list from remote`() = runTest {
        // Arrange
        fakeRemote = FakeRemotePlayerCard(emptyList())
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getTeams(testLeague)

        // Assert
        assertTrue(result.isEmpty())
        assertEquals(1, fakeLocal.setTeamsCallCount, "Should still save empty list")
    }

    @Test
    fun `getTeams should throw exception when remote fails`() = runTest {
        // Arrange
        fakeRemote = FakeRemotePlayerCard(shouldThrowError = true)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act & Assert
        assertFailsWith<Exception> {
            repository.getTeams(testLeague)
        }
        assertEquals(0, fakeLocal.setTeamsCallCount, "Should not save on error")
    }

    @Test
    fun `getTeams should handle large number of teams`() = runTest {
        // Arrange
        val remoteTeams = (1..30).map { index ->
            TestDataFactory.createTeamResponse(
                idTeam = index.toString(),
                strTeam = "Team$index"
            )
        }
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        val result = repository.getTeams(testLeague)

        // Assert
        assertEquals(30, result.size)
        assertEquals("Team1", result[0].strTeam)
        assertEquals("Team30", result[29].strTeam)
    }

    // ========== Integration Tests ==========

    @Test
    fun `should handle complete workflow for multiple leagues`() = runTest {
        // Arrange
        val league1Teams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "PSG")
        )
        val league2Teams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "2", strTeam = "Chelsea")
        )

        // Act - Fetch first league (from remote)
        fakeRemote = FakeRemotePlayerCard(league1Teams)
        repository = TeamsDataRepository(fakeRemote, fakeLocal)
        val result1 = repository.getTeams(testLeague)

        // Fetch second league (from remote)
        fakeRemote = FakeRemotePlayerCard(league2Teams)
        repository = TeamsDataRepository(fakeRemote, fakeLocal)
        val result2 = repository.getTeams(anotherLeague)

        // Fetch first league again (should be from local)
        val result3 = repository.getTeams(testLeague)

        // Assert
        assertEquals("PSG", result1[0].strTeam)
        assertEquals("Chelsea", result2[0].strTeam)
        assertEquals("PSG", result3[0].strTeam)
        assertEquals(2, fakeLocal.setTeamsCallCount)
        assertEquals(2, fakeLocal.teams.value.size)
    }

    @Test
    fun `should preserve league name format in local storage`() = runTest {
        // Arrange
        val leagueWithSpecialChars = "La Liga Santander 2024"
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "Valencia")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act
        repository.getTeams(leagueWithSpecialChars)

        // Assert
        assertEquals("La_Liga_Santander_2024", fakeRemote.lastLeagueUsed, "Spaces replaced for API")
        assertEquals(leagueWithSpecialChars, fakeLocal.lastLeagueIdSet, "Original format saved locally")

        // Verify can retrieve with original name
        val result = repository.getTeams(leagueWithSpecialChars)
        assertEquals(1, result.size)
        assertEquals("Valencia", result[0].strTeam)
    }

    @Test
    fun `should handle consecutive requests for same league efficiently`() = runTest {
        // Arrange
        val remoteTeams = listOf(
            TestDataFactory.createTeamResponse(idTeam = "1", strTeam = "Arsenal")
        )
        fakeRemote = FakeRemotePlayerCard(remoteTeams)
        fakeLocal = FakeTeamsLocalData(emptyList())
        repository = TeamsDataRepository(fakeRemote, fakeLocal)

        // Act - Multiple requests for same league
        repository.getTeams(testLeague)
        repository.getTeams(testLeague)
        repository.getTeams(testLeague)

        // Assert
        assertEquals(1, fakeRemote.getTeamsCallCount, "Should only call remote once")
        assertEquals(1, fakeLocal.setTeamsCallCount, "Should only save once")
    }
}