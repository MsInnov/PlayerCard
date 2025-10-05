package com.mscode.playercard.domain.usecase

import com.mscode.playercard.domain.repository.PlayersFavoriteRepository

class GetPlayersFavoriteUseCase(
    private val playersFavoriteRepository: PlayersFavoriteRepository
) {

    suspend fun invoke() = playersFavoriteRepository.getPlayersFavorite()

}