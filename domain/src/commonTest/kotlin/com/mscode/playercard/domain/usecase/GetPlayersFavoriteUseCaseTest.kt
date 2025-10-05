package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakePlayersFavoriteRepositoryEmpty
import com.mscode.playercard.domain.fake.FakePlayersFavoriteRepositorySuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetPlayersFavoriteUseCaseTest {

    @Test
    fun `invoke returns list of favorite players when repository has data`() = runTest {
        val repo = FakePlayersFavoriteRepositorySuccess()
        val useCase = GetPlayersFavoriteUseCase(repo)

        val result = useCase.invoke()

        assertTrue(result.isNotEmpty(), "Expected non-empty player list")
        assertEquals(3, result.size)
        assertEquals("Lionel Messi", result.first().strPlayer)
    }

    @Test
    fun `invoke returns empty list when repository has no data`() = runTest {
        val repo = FakePlayersFavoriteRepositoryEmpty()
        val useCase = GetPlayersFavoriteUseCase(repo)

        val result = useCase.invoke()

        assertTrue(result.isEmpty(), "Expected empty list when no favorites")
    }
}