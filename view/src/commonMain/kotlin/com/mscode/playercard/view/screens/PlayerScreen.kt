package com.mscode.playercard.view.screens

import androidx.compose.runtime.Composable
import com.mscode.playercard.view.navigation.LocalNavController
import com.mscode.playercard.view.navigation.PlayerCardController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mscode.playercard.view.models.PlayerUi
import com.mscode.playercard.view.models.ScreenStateUiModel
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel
import com.mscode.playercard.view.viewmodels.PlayersFavoriteViewModel.PlayerFavoriteEvent.GetPlayerFavorite
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: PlayersFavoriteViewModel = viewModel { PlayersFavoriteViewModel() },
    navController: PlayerCardController = LocalNavController.current,
    player: PlayerUi
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(player.strPlayer ?: "Player") },
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
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Box {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            val imageUrl = player.strThumb ?: player.strCutout
                            if (imageUrl != null) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = player.strPlayer,
                                    modifier = Modifier
                                        .size(180.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(180.dp)
                                        .background(Color.Gray, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("No image", color = Color.White)
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = player.strPlayer ?: "Unknown",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )

                            Text(
                                text = "${player.strTeam ?: ""} #${player.strNumber ?: "-"}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(8.dp))

                            InfoRow(label = "Position", value = player.strPosition)
                            InfoRow(label = "Nationality", value = player.strNationality)
                            InfoRow(label = "Gender", value = player.strGender)
                            InfoRow(label = "Date of Birth", value = player.dateBorn)
                            InfoRow(label = "Place of Birth", value = player.strBirthLocation)
                            InfoRow(label = "Height / Weight", value = "${player.strHeight ?: "-"} / ${player.strWeight ?: "-"}")

                            Spacer(Modifier.height(8.dp))

                            val links = listOfNotNull(
                                player.strWebsite,
                                player.strFacebook,
                                player.strTwitter,
                                player.strInstagram,
                                player.strYoutube
                            )
                            if (links.isNotEmpty()) {
                                Text("Link", fontWeight = FontWeight.SemiBold)
                                links.forEach { link ->
                                    Text(
                                        text = link,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            val description = player.strDescriptionFR ?: player.strDescriptionEN
                            if (!description.isNullOrEmpty()) {
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                    viewModel.onEvent(GetPlayerFavorite(player.idPlayer))
                    val isFavoriteValue = (viewModel.uiModelIsFavorite.value as ScreenStateUiModel.Success).content
                    var isFavoriteClicked by remember { mutableStateOf(isFavoriteValue) }
                    IconButton(
                        onClick = {
                            isFavoriteClicked = !isFavoriteClicked
                            if(isFavoriteClicked) {
                                viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.SavePlayerFavorite(player = player))
                            } else {
                                viewModel.onEvent(PlayersFavoriteViewModel.PlayerFavoriteEvent.RemovePlayerFavorite(playerId = player.idPlayer))
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavoriteClicked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavoriteClicked) "Unfavorite" else "Favorite",
                            tint = if (isFavoriteClicked) Color.Red else Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
        ) {
            Text("$label: ", fontWeight = FontWeight.SemiBold)
            Text(value)
        }
    }
}