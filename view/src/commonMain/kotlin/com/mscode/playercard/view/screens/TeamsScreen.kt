package com.mscode.playercard.view.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.models.TeamUi
import com.mscode.playercard.view.navigation.LocalNavController
import com.mscode.playercard.view.navigation.PlayerCardController
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel.PlayerFavoriteEvent.GetAllPlayersFavorite
import com.mscode.playercard.view.viewmodels.TeamsViewModel
import com.mscode.playercard.view.widget.FavoriteContentBottomSheet
import com.mscode.playercard.view.widget.FavoritesBottomSheet
import playercard.view.generated.resources.Res
import playercard.view.generated.resources.playercard
import org.jetbrains.compose.resources.painterResource
import playercard.view.generated.resources.favorite_header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(
    navController: PlayerCardController = LocalNavController.current,
    viewModelFavorite: PlayersFavoriteViewModel = viewModel { PlayersFavoriteViewModel() },
    viewModel: TeamsViewModel = viewModel { TeamsViewModel() },
    onTeamSelected: (String) -> Unit
) {
    val teamsState = viewModel.uiModel.collectAsState()
    var showFavorites by remember { mutableStateOf(false) }
    val favorites = viewModelFavorite.uiModelGetFavorites.collectAsState()
    viewModelFavorite.onEvent(GetAllPlayersFavorite)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose your Team", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.goBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Return",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(
                        onClick = {
                            showFavorites = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.favorite_header),
                            contentDescription = "Favorites",
                            tint = if(favorites.value.isNotEmpty()) Yellow else Color.Gray
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (val teams = teamsState.value) {
            is ScreenStateUiModel.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    items(teams.content) { team ->
                        TeamItem(
                            team = team,
                            onClick = { onTeamSelected(team.teamId) }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
                FavoritesBottomSheet(show = showFavorites, onDismiss = { showFavorites = false }) {
                    FavoriteContentBottomSheet(favorites.value)
                }
            }

            is ScreenStateUiModel.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            else -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading Error ⚠️", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun TeamItem(
    team: TeamUi,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(team.teamId) }
            .padding(vertical = 4.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (!team.strBadge.isNullOrEmpty()) {
                    AsyncImage(
                        model = team.strBadge,
                        contentDescription = team.strTeam,
                        modifier = Modifier.size(56.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.playercard),
                        contentDescription = "Logo Missing",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = team.strTeam,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                team.strLeague?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    team.strCountry?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    team.strStadium?.let {
                        Text(
                            text = " • $it",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    team.intFormedYear?.let {
                        Text(
                            text = "Since $it",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    }
}

