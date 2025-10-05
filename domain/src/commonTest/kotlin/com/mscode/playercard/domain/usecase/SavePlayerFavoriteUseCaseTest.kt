package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakePlayersFavoriteRepositorySuccess
import com.mscode.playercard.domain.fake.TestDomainFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SavePlayerFavoriteUseCaseTest {

    @Test
    fun `invoke calls savePlayerFavorite with correct player`() = runTest {
        val repo = FakePlayersFavoriteRepositorySuccess()
        val useCase = SavePlayerFavoriteUseCase(repo)

        val player1 = TestDomainFactory.createPlayer("10", "Messi")

        useCase.invoke(player1)
        val expected = listOf(
            TestDomainFactory.createPlayer("1", "Messi"),
            TestDomainFactory.createPlayer("2", "Mbappé"),
            TestDomainFactory.createPlayer("3", "Haaland"),
            player1
        )
        val actual = repo.getPlayersFavorite()

        assertEquals(expected[0].strPlayer, actual[0].strPlayer)
        assertEquals(expected[1].strPlayer, actual[1].strPlayer)
        assertEquals(expected[2].strPlayer, actual[2].strPlayer)
        assertEquals(expected[3].strPlayer, actual[3].strPlayer)
    }

}