package com.mscode.playercard.view.viewmodel

import app.cash.turbine.test
import com.mscode.playercard.domain.repository.PlayersRepository
import com.mscode.playercard.domain.usecase.GetPlayersUseCase
import com.mscode.playercard.view.fake.FakePlayersRepositoryError
import com.mscode.playercard.view.fake.FakePlayersRepositorySuccess
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.viewmodels.PlayersViewModel
import com.mscode.playercard.view.viewmodels.PlayersViewModel.PlayersEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlayersViewModelTest {

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
    fun `when GetPlayersUseCase returns Success then uiModel have player list`() = runTest {
        initKoin(
            module {
                single<PlayersRepository> { FakePlayersRepositorySuccess() }
                single { GetPlayersUseCase(get()) }
            }
        )

        val viewModel = PlayersViewModel()
        viewModel.onEvent(PlayersEvent.GetPlayers("Messi"))

        viewModel.uiModel.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val success = awaitItem()
            assertTrue(success is ScreenStateUiModel.Success)
            assertEquals(success.content[0].strPlayer,"Messi")
        }
    }

    @Test
    fun `when GetPlayersUseCase returns Error then uiModel have error message`() = runTest {
        initKoin(
            module {
                single<PlayersRepository> { FakePlayersRepositoryError() }
                single { GetPlayersUseCase(get()) }
            }
        )

        val viewModel = PlayersViewModel()
        viewModel.onEvent(PlayersEvent.GetPlayers("Messi"))

        viewModel.uiModel.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val error = awaitItem()
            assertTrue(error is ScreenStateUiModel.Error)
            assertEquals(error.errorInfo,"Network error for teamId=Messi")
        }
    }
}