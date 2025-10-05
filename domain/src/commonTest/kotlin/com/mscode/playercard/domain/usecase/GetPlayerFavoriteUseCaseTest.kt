package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.fake.FakePlayersFavoriteRepositoryEmpty
import com.mscode.playercard.domain.fake.FakePlayersFavoriteRepositorySuccess
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GetPlayerFavoriteUseCaseTest {

    @Test
    fun `invoke returns player when found`() = runTest {
        val repo = FakePlayersFavoriteRepositorySuccess()
        val useCase = GetPlayerFavoriteUseCase(repo)

        val result = useCase.invoke("1")

        assertNotNull(result)
        assertEquals("Lionel Messi", result.strPlayer)
    }

    @Test
    fun `invoke returns null when player not found`() = runTest {
        val repo = FakePlayersFavoriteRepositoryEmpty()
        val useCase = GetPlayerFavoriteUseCase(repo)

        val result = useCase.invoke("99")

        assertNull(result)
    }

}