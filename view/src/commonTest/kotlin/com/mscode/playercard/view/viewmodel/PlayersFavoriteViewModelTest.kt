package com.mscode.playercard.view.viewmodel

import app.cash.turbine.test
import com.mscode.playercard.domain.repository.PlayersFavoriteRepository
import com.mscode.playercard.domain.usecase.GetPlayerFavoriteUseCase
import com.mscode.playercard.domain.usecase.GetPlayersFavoriteUseCase
import com.mscode.playercard.domain.usecase.SavePlayerFavoriteUseCase
import com.mscode.playercard.view.fake.FakePlayersFavoriteRepositoryEmpty
import com.mscode.playercard.view.fake.FakePlayersFavoriteRepositorySuccess
import com.mscode.playercard.view.fake.TestViewFactory
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.koin.dsl.module
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PlayersFavoriteViewModelTest {

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
    fun `when GetPlayerFavoriteUseCase returns null then isFavorite = false`() = runTest {
        initKoin(
            module {
                single<PlayersFavoriteRepository> { FakePlayersFavoriteRepositoryEmpty() }
                single { GetPlayerFavoriteUseCase(get()) }
            }
        )

        val viewModel = PlayersFavoriteViewModel()
        viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.GetPlayerFavorite("123"))

        viewModel.uiModelIsFavorite.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val success = awaitItem()
            assertTrue(success is ScreenStateUiModel.Success)
            assertFalse(success.content)
        }
    }

    @Test
    fun `when GetPlayerFavoriteUseCase returns a player then isFavorite = true`() = runTest {
        initKoin(
            module {
                single<PlayersFavoriteRepository> {
                    val rep = FakePlayersFavoriteRepositorySuccess()
                    rep.reloadFavorites()
                    rep
                }
                single { GetPlayerFavoriteUseCase(get()) }
            }
        )

        val viewModel = PlayersFavoriteViewModel()
        viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.GetPlayerFavorite("1"))

        viewModel.uiModelIsFavorite.test {
            val loading = awaitItem()
            assertTrue(loading is ScreenStateUiModel.Loading)

            val success = awaitItem()
            assertTrue(success is ScreenStateUiModel.Success)
            assertTrue(success.content)
        }
    }

    @Test
    fun `when GetPlayersFavoriteUseCase is called uiModelGetFavorites updates`() = runTest {
        initKoin(
            module {
                single<PlayersFavoriteRepository> {
                    val rep = FakePlayersFavoriteRepositorySuccess()
                    rep.reloadFavorites()
                    rep
                }
                single { GetPlayersFavoriteUseCase(get()) }
            }
        )

        val viewModel = PlayersFavoriteViewModel()
        viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.GetAllPlayersFavorite)

        viewModel.uiModelGetFavorites.test {
            awaitItem()
            val result = awaitItem()
            assertEquals("Messi", result[0].strPlayer)
            assertEquals("Mbappé", result[1].strPlayer)
            assertEquals("Haaland", result[2].strPlayer)
        }
    }

    @Test
    fun `when SavePlayerFavoriteUseCase is called uiModelGetFavorites updates`() = runTest {
        initKoin(
            module {
                single<PlayersFavoriteRepository> {
                    val rep = FakePlayersFavoriteRepositorySuccess()
                    rep.reloadFavorites()
                    rep
                }
                single { SavePlayerFavoriteUseCase(get()) }
                single { GetPlayersFavoriteUseCase(get()) }
            }
        )

        val viewModel = PlayersFavoriteViewModel()
        viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.GetAllPlayersFavorite)
        viewModel.uiModelGetFavorites.test {
            awaitItem()
            val result = awaitItem()
            assertEquals("Messi", result[0].strPlayer)
            assertEquals("Mbappé", result[1].strPlayer)
            assertEquals("Haaland", result[2].strPlayer)
            viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.SavePlayerFavorite(TestViewFactory.createPlayerUi(idPlayer = "4", strPlayer = "Cocard")))
            viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.GetAllPlayersFavorite)
            val result2 = awaitItem()
            assertEquals("Messi", result2[0].strPlayer)
            assertEquals("Mbappé", result2[1].strPlayer)
            assertEquals("Haaland", result2[2].strPlayer)
            assertEquals("Cocard", result2[3].strPlayer)
        }
    }
}