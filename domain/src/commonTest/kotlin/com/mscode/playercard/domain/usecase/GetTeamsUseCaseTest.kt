package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakeTeamsRepositoryError
import com.mscode.playercard.domain.fake.FakeTeamsRepositorySuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetTeamsUseCaseTest {

    @Test
    fun `invoke returns success when repository succeeds`() = runTest {
        val repo = FakeTeamsRepositorySuccess()
        val useCase = GetTeamsUseCase(repo)

        val result = useCase.invoke("Ligue 1")

        assertTrue(result.isSuccess, "Expected result to be success")
        val teams = result.getOrNull()
        assertNotNull(teams)
        assertEquals(3, teams.size)
        assertEquals("PSG", teams.first().strTeam)
    }

    @Test
    fun `invoke returns failure when repository throws`() = runTest {
        val repo = FakeTeamsRepositoryError()
        val useCase = GetTeamsUseCase(repo)

        val result = useCase.invoke("Premier League")

        assertTrue(result.isFailure, "Expected result to be failure")
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception.message?.contains("Network error") == true)
    }

}