package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class RemovePlayerFavoriteUseCase(
    private val playersFavoriteRepository: PlayersFavoriteRepository
) {

    fun invoke(playerId: String) = playersFavoriteRepository.removePlayerFavorite(playerId)

}