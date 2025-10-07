package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class GetPlayerFavoriteUseCase(
    private val playersFavoriteRepository: PlayersFavoriteRepository
) {

    fun invoke(playerId: String) = playersFavoriteRepository.getPlayerFavorite(playerId)

}