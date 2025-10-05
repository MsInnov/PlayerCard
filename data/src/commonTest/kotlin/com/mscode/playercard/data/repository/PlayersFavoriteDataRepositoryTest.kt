package com.mscode.playercard.data.repository

import com.mscode.playercard.data.fake.FakeFavoritesPlayersLocalDataSource
import com.mscode.playercard.data.fake.TestDataFactory
import kotlin.test.*

class PlayersFavoriteDataRepositoryTest {

    private lateinit var repository: PlayersFavoriteDataRepository
    private lateinit var fakeLocalDataSource: FakeFavoritesPlayersLocalDataSource

    @BeforeTest
    fun setup() {
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource()
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)
    }

    @Test
    fun `getPlayersFavorite should return empty list when no favorites`() {
        // Arrange
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(emptyList())
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        val result = repository.getPlayersFavorite()

        // Assert
        assertTrue(result.isEmpty())
        assertEquals(1, fakeLocalDataSource.getPlayersCallCount)
    }

    @Test
    fun `getPlayersFavorite should return all favorite players`() {
        // Arrange
        val favoritePlayers = listOf(
            TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Messi"),
            TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Ronaldo"),
            TestDataFactory.createPlayer(idPlayer = "3", strPlayer = "Neymar")
        )
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(favoritePlayers)
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        val result = repository.getPlayersFavorite()

        // Assert
        assertEquals(3, result.size)
        assertEquals("Messi", result[0].strPlayer)
        assertEquals("Ronaldo", result[1].strPlayer)
        assertEquals("Neymar", result[2].strPlayer)
        assertEquals(1, fakeLocalDataSource.getPlayersCallCount)
    }

    @Test
    fun `getPlayersFavorite should return current state of favorites`() {
        // Arrange
        val player1 = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Player1")
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(listOf(player1))
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act - First call
        val result1 = repository.getPlayersFavorite()

        // Add a new favorite
        val player2 = TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Player2")
        repository.savePlayerFavorite(player2)

        // Act - Second call
        val result2 = repository.getPlayersFavorite()

        // Assert
        assertEquals(1, result1.size)
        assertEquals(2, result2.size)
        assertEquals(2, fakeLocalDataSource.getPlayersCallCount)
    }

    @Test
    fun `getPlayerFavorite should return null when player not found`() {
        // Arrange
        val playerId = "999"
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(emptyList())
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        val result = repository.getPlayerFavorite(playerId)

        // Assert
        assertNull(result)
        assertEquals(1, fakeLocalDataSource.getPlayerCallCount)
        assertEquals(playerId, fakeLocalDataSource.lastPlayerIdQueried)
    }

    @Test
    fun `getPlayerFavorite should return player when found`() {
        // Arrange
        val targetPlayer = TestDataFactory.createPlayer(
            idPlayer = "123",
            strPlayer = "Mbappé",
            strNationality = "France"
        )
        val favoritePlayers = listOf(
            TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Messi"),
            targetPlayer,
            TestDataFactory.createPlayer(idPlayer = "3", strPlayer = "Neymar")
        )
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(favoritePlayers)
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        val result = repository.getPlayerFavorite("123")

        // Assert
        assertNotNull(result)
        assertEquals("123", result.idPlayer)
        assertEquals("Mbappé", result.strPlayer)
        assertEquals("France", result.strNationality)
        assertEquals(1, fakeLocalDataSource.getPlayerCallCount)
        assertEquals("123", fakeLocalDataSource.lastPlayerIdQueried)
    }

    @Test
    fun `getPlayerFavorite should return correct player among many favorites`() {
        // Arrange
        val players = (1..10).map { index ->
            TestDataFactory.createPlayer(
                idPlayer = index.toString(),
                strPlayer = "Player$index"
            )
        }
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(players)
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        val result = repository.getPlayerFavorite("5")

        // Assert
        assertNotNull(result)
        assertEquals("5", result.idPlayer)
        assertEquals("Player5", result.strPlayer)
    }

    // ========== savePlayerFavorite Tests ==========

    @Test
    fun `savePlayerFavorite should add new player to favorites`() {
        // Arrange
        val newPlayer = TestDataFactory.createPlayer(
            idPlayer = "100",
            strPlayer = "Haaland"
        )
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(emptyList())
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        repository.savePlayerFavorite(newPlayer)

        // Assert
        assertEquals(1, fakeLocalDataSource.savePlayerCallCount)
        assertEquals(newPlayer, fakeLocalDataSource.lastPlayerSaved)

        val favorites = repository.getPlayersFavorite()
        assertEquals(1, favorites.size)
        assertEquals("Haaland", favorites[0].strPlayer)
    }

    @Test
    fun `savePlayerFavorite should update existing player`() {
        // Arrange
        val originalPlayer = TestDataFactory.createPlayer(
            idPlayer = "1",
            strPlayer = "Original Name",
            strPosition = "Forward"
        )
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(listOf(originalPlayer))
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        val updatedPlayer = originalPlayer.copy(
            strPlayer = "Updated Name",
            strPosition = "Midfielder"
        )

        // Act
        repository.savePlayerFavorite(updatedPlayer)

        // Assert
        assertEquals(1, fakeLocalDataSource.savePlayerCallCount)

        val result = repository.getPlayerFavorite("1")
        assertNotNull(result)
        assertEquals("Updated Name", result.strPlayer)
        assertEquals("Midfielder", result.strPosition)
    }

    @Test
    fun `savePlayerFavorite should add multiple different players`() {
        // Arrange
        val player1 = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Player1")
        val player2 = TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Player2")
        val player3 = TestDataFactory.createPlayer(idPlayer = "3", strPlayer = "Player3")

        // Act
        repository.savePlayerFavorite(player1)
        repository.savePlayerFavorite(player2)
        repository.savePlayerFavorite(player3)

        // Assert
        assertEquals(3, fakeLocalDataSource.savePlayerCallCount)

        val favorites = repository.getPlayersFavorite()
        assertEquals(3, favorites.size)
    }

    @Test
    fun `savePlayerFavorite should preserve all player data`() {
        // Arrange
        val completePlayer = TestDataFactory.createPlayer(
            idPlayer = "123",
            idTeam = "456",
            strPlayer = "Complete Player",
            strNationality = "Brazil",
            strPosition = "Striker"
        )

        // Act
        repository.savePlayerFavorite(completePlayer)

        // Assert
        val result = repository.getPlayerFavorite("123")
        assertNotNull(result)
        assertEquals("123", result.idPlayer)
        assertEquals("456", result.idTeam)
        assertEquals("Complete Player", result.strPlayer)
        assertEquals("Brazil", result.strNationality)
        assertEquals("Striker", result.strPosition)
    }

    @Test
    fun `removePlayerFavorite should remove player from favorites`() {
        // Arrange
        val player1 = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Player1")
        val player2 = TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Player2")
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(listOf(player1, player2))
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        repository.removePlayerFavorite("1")

        // Assert
        assertEquals(1, fakeLocalDataSource.clearPlayerCallCount)
        assertEquals("1", fakeLocalDataSource.lastPlayerIdCleared)

        val favorites = repository.getPlayersFavorite()
        assertEquals(1, favorites.size)
        assertEquals("Player2", favorites[0].strPlayer)
    }

    @Test
    fun `removePlayerFavorite should handle non-existent player gracefully`() {
        // Arrange
        val player = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Player1")
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(listOf(player))
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act - Remove non-existent player
        repository.removePlayerFavorite("999")

        // Assert
        assertEquals(1, fakeLocalDataSource.clearPlayerCallCount)
        assertEquals("999", fakeLocalDataSource.lastPlayerIdCleared)

        // Original player should still be there
        val favorites = repository.getPlayersFavorite()
        assertEquals(1, favorites.size)
        assertEquals("Player1", favorites[0].strPlayer)
    }

    @Test
    fun `removePlayerFavorite should remove all instances of player`() {
        // Arrange
        val player1 = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Player1")
        val player2 = TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Player2")
        val player3 = TestDataFactory.createPlayer(idPlayer = "3", strPlayer = "Player3")
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(
            listOf(player1, player2, player3)
        )
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        repository.removePlayerFavorite("2")

        // Assert
        val favorites = repository.getPlayersFavorite()
        assertEquals(2, favorites.size)
        assertNull(repository.getPlayerFavorite("2"))
        assertNotNull(repository.getPlayerFavorite("1"))
        assertNotNull(repository.getPlayerFavorite("3"))
    }

    @Test
    fun `removePlayerFavorite should result in empty list when last player removed`() {
        // Arrange
        val singlePlayer = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "OnlyPlayer")
        fakeLocalDataSource = FakeFavoritesPlayersLocalDataSource(listOf(singlePlayer))
        repository = PlayersFavoriteDataRepository(fakeLocalDataSource)

        // Act
        repository.removePlayerFavorite("1")

        // Assert
        val favorites = repository.getPlayersFavorite()
        assertTrue(favorites.isEmpty())
        assertNull(repository.getPlayerFavorite("1"))
    }

    @Test
    fun `should handle complete workflow of add get and remove`() {
        // Arrange
        val player1 = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "Messi")
        val player2 = TestDataFactory.createPlayer(idPlayer = "2", strPlayer = "Ronaldo")

        // Act & Assert - Add first player
        repository.savePlayerFavorite(player1)
        assertEquals(1, repository.getPlayersFavorite().size)

        // Add second player
        repository.savePlayerFavorite(player2)
        assertEquals(2, repository.getPlayersFavorite().size)

        // Get specific player
        val retrieved = repository.getPlayerFavorite("1")
        assertNotNull(retrieved)
        assertEquals("Messi", retrieved.strPlayer)

        // Remove one player
        repository.removePlayerFavorite("1")
        assertEquals(1, repository.getPlayersFavorite().size)
        assertNull(repository.getPlayerFavorite("1"))

        // Verify remaining player
        val remaining = repository.getPlayerFavorite("2")
        assertNotNull(remaining)
        assertEquals("Ronaldo", remaining.strPlayer)
    }

    @Test
    fun `should maintain data integrity across multiple operations`() {
        // Arrange
        val players = (1..5).map { index ->
            TestDataFactory.createPlayer(
                idPlayer = index.toString(),
                strPlayer = "Player$index"
            )
        }

        // Act - Add all players
        players.forEach { repository.savePlayerFavorite(it) }
        assertEquals(5, repository.getPlayersFavorite().size)

        // Remove odd-numbered players
        repository.removePlayerFavorite("1")
        repository.removePlayerFavorite("3")
        repository.removePlayerFavorite("5")

        // Assert
        val favorites = repository.getPlayersFavorite()
        assertEquals(2, favorites.size)
        assertNotNull(repository.getPlayerFavorite("2"))
        assertNotNull(repository.getPlayerFavorite("4"))
        assertNull(repository.getPlayerFavorite("1"))
        assertNull(repository.getPlayerFavorite("3"))
        assertNull(repository.getPlayerFavorite("5"))
    }

    @Test
    fun `should handle rapid successive operations`() {
        // Arrange
        val player = TestDataFactory.createPlayer(idPlayer = "1", strPlayer = "TestPlayer")

        // Act - Rapid add/remove/add
        repository.savePlayerFavorite(player)
        repository.removePlayerFavorite("1")
        repository.savePlayerFavorite(player)

        // Assert
        assertEquals(1, repository.getPlayersFavorite().size)
        assertNotNull(repository.getPlayerFavorite("1"))
        assertEquals(2, fakeLocalDataSource.savePlayerCallCount)
        assertEquals(1, fakeLocalDataSource.clearPlayerCallCount)
    }
}