package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakeLeaguesRepositoryError
import com.mscode.playercard.domain.fake.FakeLeaguesRepositorySuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetLeaguesUseCaseTest {

    @Test
    fun `invoke returns success when repository succeeds`() = runTest {
        val repo = FakeLeaguesRepositorySuccess()
        val useCase = GetLeaguesUseCase(repo)

        val result = useCase.invoke()

        assertTrue(result.isSuccess)
        val leagues = result.getOrNull()
        assertNotNull(leagues)
        assertEquals(2, leagues.size)
        assertEquals("Premier League", leagues.first().league)
    }

    @Test
    fun `invoke returns failure when repository throws`() = runTest {
        val repo = FakeLeaguesRepositoryError()
        val useCase = GetLeaguesUseCase(repo)

        val result = useCase.invoke()

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertEquals("Network error", exception.message)
    }

}