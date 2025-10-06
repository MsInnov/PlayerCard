package com.mscode.playercard.view.viewmodel

import app.cash.turbine.test
import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.domain.usecase.GetLeaguesUseCase
import com.mscode.playercard.view.fake.FakeLeaguesRepositoryError
import com.mscode.playercard.view.fake.FakeLeaguesRepositorySuccess
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.viewmodels.LeaguesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.koin.dsl.module
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.stopKoin
import kotlin.test.*

class LeaguesViewModelTest {

    @BeforeTest
    fun setup() {
        stopKoin()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `uiModel emits Success when use case returns leagues`() = runTest {
        initKoin (
            module {
                single<LeaguesRepository> { FakeLeaguesRepositorySuccess() }
                single { GetLeaguesUseCase(get()) }
            }
        )

        val viewModel = LeaguesViewModel()

        viewModel.uiModel.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val success = awaitItem()
            assertTrue(success is ScreenStateUiModel.Success)
            assertEquals(listOf("Premier League", "NBA"), success.content)
        }
    }

    @Test
    fun `uiModel emits Error when use case fails`() = runTest {
        initKoin(
            module {
                single<LeaguesRepository> { FakeLeaguesRepositoryError() }
                single { GetLeaguesUseCase(get()) }
            }
        )

        val viewModel = LeaguesViewModel()

        viewModel.uiModel.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val error = awaitItem()
            assertTrue(error is ScreenStateUiModel.Error)
            assertEquals("Network error", error.errorInfo)
        }
    }
}