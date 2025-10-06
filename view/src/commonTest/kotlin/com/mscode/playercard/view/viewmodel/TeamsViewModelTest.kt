package com.mscode.playercard.view.viewmodel

import app.cash.turbine.test
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.domain.usecase.GetTeamsUseCase
import com.mscode.playercard.view.fake.FakeTeamsRepositoryError
import com.mscode.playercard.view.fake.FakeTeamsRepositorySuccess
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.viewmodels.TeamsViewModel
import com.mscode.playercard.view.viewmodels.TeamsViewModel.TeamsEvent
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

class TeamsViewModelTest {

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
    fun `when GetTeamsUseCase returns Success then uiModel have team list`() = runTest {
        initKoin(
            module {
                single<TeamsRepository> { FakeTeamsRepositorySuccess() }
                single { GetTeamsUseCase(get()) }
            }
        )

        val viewModel = TeamsViewModel()
        viewModel.onEvent(TeamsEvent.GetTeam("PSG"))

        viewModel.uiModel.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val success = awaitItem()
            assertTrue(success is ScreenStateUiModel.Success)
            assertEquals(success.content[0].strTeam,"PSG")
        }
    }

    @Test
    fun `when GetTeamsUseCase returns Error then uiModel have error message`() = runTest {
        initKoin(
            module {
                single<TeamsRepository> { FakeTeamsRepositoryError() }
                single { GetTeamsUseCase(get()) }
            }
        )

        val viewModel = TeamsViewModel()
        viewModel.onEvent(TeamsEvent.GetTeam("PSG"))

        viewModel.uiModel.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val error = awaitItem()
            assertTrue(error is ScreenStateUiModel.Error)
            assertEquals(error.errorInfo,"Network error for league=PSG")
        }
    }
}