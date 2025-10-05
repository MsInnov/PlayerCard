package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakePlayersRepositoryError
import com.mscode.playercard.domain.fake.FakePlayersRepositorySuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetPlayersUseCaseTest {

    @Test
    fun `invoke returns success when repository succeeds`() = runTest {
        val repo = FakePlayersRepositorySuccess()
        val useCase = GetPlayersUseCase(repo)

        val result = useCase.invoke("123")

        assertTrue(result.isSuccess, "Expected result to be success")
        val players = result.getOrNull()
        assertNotNull(players)
        assertEquals(3, players.size)
        assertEquals("Lionel Messi", players.first().strPlayer)
    }

    @Test
    fun `invoke returns failure when repository throws`() = runTest {
        val repo = FakePlayersRepositoryError()
        val useCase = GetPlayersUseCase(repo)

        val result = useCase.invoke("999")

        assertTrue(result.isFailure, "Expected result to be failure")
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception.message?.contains("Network error") == true)
    }

}