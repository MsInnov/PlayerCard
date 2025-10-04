package com.mscode.playercard.view.screens
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mscode.playercard.view.models.PlayerUi
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.navigation.LocalNavController
import com.mscode.playercard.view.navigation.NavigationScreen
import com.mscode.playercard.view.navigation.PlayerCardController
import com.mscode.playercard.view.viewmodels.PlayersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreen(
    navController: PlayerCardController = LocalNavController.current,
    viewModel: PlayersViewModel = viewModel { PlayersViewModel() },
    onPlayer: (player: PlayerUi) -> Unit
) {
    val playersState = viewModel.uiModel.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Players", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.goBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Return", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        when (val players = playersState.value) {
            is ScreenStateUiModel.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(players.content) { player ->
                        PlayerCard(
                            navController = navController,
                            player = player,
                            onPlayer = onPlayer
                        )
                    }
                }
            }

            is ScreenStateUiModel.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is ScreenStateUiModel.Error -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(players.errorInfo, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun PlayerCard(
    navController: PlayerCardController,
    player: PlayerUi,
    onPlayer: (player: PlayerUi) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(NavigationScreen.PLAYER)
                onPlayer(player)
            }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = player.strRender ?: player.strThumb ?: player.strCutout,
                contentDescription = player.strPlayer,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = player.strPlayer ?: "Unknown name",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1
            )
            Text(
                text = player.strPosition ?: "Unknown position",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(4.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!player.strTeam.isNullOrEmpty()) Text(player.strTeam, style = MaterialTheme.typography.bodySmall)
                if (!player.dateBorn.isNullOrEmpty()) Text("Born on ${player.dateBorn}", style = MaterialTheme.typography.bodySmall)
                if (!player.strNationality.isNullOrEmpty()) Text(player.strNationality, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
