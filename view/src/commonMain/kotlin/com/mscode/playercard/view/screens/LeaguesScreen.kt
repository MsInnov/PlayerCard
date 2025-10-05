package com.mscode.playercard.view.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.viewmodels.LeaguesViewModel
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel.PlayerFavoriteEvent.GetAllPlayersFavorite
import com.mscode.playercard.view.widget.FavoriteContentBottomSheet
import com.mscode.playercard.view.widget.FavoritesBottomSheet
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import playercard.view.generated.resources.Res
import playercard.view.generated.resources.belgium_pro_league_supercup
import playercard.view.generated.resources.dutch_eredivisie
import playercard.view.generated.resources.english_league_championship
import playercard.view.generated.resources.english_premier_league
import playercard.view.generated.resources.favorite_header
import playercard.view.generated.resources.french_ligue_1
import playercard.view.generated.resources.german_bundesliga
import playercard.view.generated.resources.greek_superleague_greece
import playercard.view.generated.resources.ic_trophy
import playercard.view.generated.resources.italian_serie_a
import playercard.view.generated.resources.scottish_championship
import playercard.view.generated.resources.spanish_la_ligua

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaguesScreen(
    viewModelFavorite: PlayersFavoriteViewModel = viewModel { PlayersFavoriteViewModel() },
    viewModel: LeaguesViewModel = viewModel { LeaguesViewModel() },
    onLeagueSelected: (String) -> Unit
) {
    val leaguesState = viewModel.uiModel.collectAsState()
    val favorites = viewModelFavorite.uiModelGetFavorites.collectAsState()
    viewModelFavorite.onEvent(GetAllPlayersFavorite)
    var showFavorites by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select your League", fontWeight = FontWeight.Bold) },
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
        when (val leagues = leaguesState.value) {
            is ScreenStateUiModel.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    items(leagues.content) { league ->
                        LeagueCard(
                            leagueName = league,
                            onClick = { onLeagueSelected(league) }
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
fun LeagueCard(leagueName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(resource = getIcon(leagueName)),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = leagueName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}


private fun getIcon(nameLeague: String): DrawableResource =
    when(nameLeague) {
        "English Premier League" -> Res.drawable.english_premier_league
        "English League Championship" -> Res.drawable.english_league_championship
        "Scottish Premier League" -> Res.drawable.scottish_championship
        "German Bundesliga" -> Res.drawable.german_bundesliga
        "Italian Serie A" -> Res.drawable.italian_serie_a
        "French Ligue 1" -> Res.drawable.french_ligue_1
        "Spanish La Liga" -> Res.drawable.spanish_la_ligua
        "Greek Superleague Greece" -> Res.drawable.greek_superleague_greece
        "Dutch Eredivisie" -> Res.drawable.dutch_eredivisie
        "Belgian Pro League" -> Res.drawable.belgium_pro_league_supercup
        else -> Res.drawable.ic_trophy
    }


