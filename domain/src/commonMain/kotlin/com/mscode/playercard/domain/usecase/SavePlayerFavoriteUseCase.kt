package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.models.Player
import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class SavePlayerFavoriteUseCase(
    private val playersFavoriteRepository: PlayersFavoriteRepository
) {

    suspend fun invoke(player: Player) = playersFavoriteRepository.savePlayerFavorite(player)

}