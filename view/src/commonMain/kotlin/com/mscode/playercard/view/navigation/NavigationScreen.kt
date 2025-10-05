package com.mscode.playercard.view.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mscode.playercard.view.models.PlayerUi
import com.mscode.playercard.view.screens.LeaguesScreen
import com.mscode.playercard.view.screens.PlayerScreen
import com.mscode.playercard.view.screens.PlayersScreen
import com.mscode.playercard.view.screens.TeamsScreen
import com.mscode.playercard.view.theme.PlayerCardAppTheme
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel
import com.mscode.playercard.view.viewmodels.PlayersViewModel
import com.mscode.playercard.view.viewmodels.PlayersViewModel.PlayersEvent.GetPlayers
import com.mscode.playercard.view.viewmodels.TeamsViewModel
import com.mscode.playercard.view.viewmodels.TeamsViewModel.TeamsEvent.GetTeam

@Composable
fun PlayerCardNavHost(
    navigationViewModel: NavigationViewModel = NavigationViewModel()
) {
    var league = ""
    var teamId = ""
    var playerUi: PlayerUi? =  null
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController: NavHostController = rememberNavController()
    PlayerCardAppTheme {
        CompositionLocalProvider(LocalNavController provides navigationViewModel) {
            NavHost(
                navController = navController,
                startDestination = NavigationScreen.LEAGUES.name,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                composable(route = NavigationScreen.LEAGUES.name) {
                    LeaguesScreen(
                        onLeagueSelected = { leagueClicked ->
                            league = leagueClicked
                            navigationViewModel.navigate(NavigationScreen.TEAMS)
                        }
                    )
                }
                composable(route = NavigationScreen.TEAMS.name) {
                    val viewModel = viewModel { TeamsViewModel() }
                    viewModel.onEvent(GetTeam(league))
                    TeamsScreen(
                        viewModel = viewModel,
                        onTeamSelected = { teamIdClicked ->
                            teamId = teamIdClicked
                            navigationViewModel.navigate(NavigationScreen.PLAYERS)
                        }
                    )
                }
                composable(route = NavigationScreen.PLAYERS.name) {
                    val viewModel = viewModel { PlayersViewModel() }
                    viewModel.onEvent(GetPlayers(teamId))
                    PlayersScreen(
                        viewModel = viewModel,
                        onPlayer = { onPlayerUi ->
                            playerUi = onPlayerUi
                        }
                    )
                }
                composable(route = NavigationScreen.PLAYER.name) {
                    playerUi?.let {
                        PlayerScreen(
                            player = it
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(navigationViewModel.navigationFlow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationViewModel.navigationFlow.collect { screen ->
                screen?.let {
                    navController.navigate(it.name)
                } ?: navController.popBackStack()
            }
        }
    }
}