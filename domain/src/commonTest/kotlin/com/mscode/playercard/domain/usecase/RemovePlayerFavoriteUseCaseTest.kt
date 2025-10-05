package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakePlayersFavoriteRepositorySuccess
import com.mscode.playercard.domain.fake.TestDomainFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RemovePlayerFavoriteUseCaseTest {

    @Test
    fun `invoke calls removePlayerFavorite with correct id`() = runTest {
        val repo = FakePlayersFavoriteRepositorySuccess()
        val useCase = RemovePlayerFavoriteUseCase(repo)

        useCase.invoke("1")
        val expected = listOf(
            TestDomainFactory.createPlayer("2", "Mbappé"),
            TestDomainFactory.createPlayer("3", "Haaland")
        )
        val actual = repo.getPlayersFavorite()
        assertEquals(expected[0].strPlayer, actual[0].strPlayer)
        assertEquals(expected[1].strPlayer, actual[1].strPlayer)
    }

}