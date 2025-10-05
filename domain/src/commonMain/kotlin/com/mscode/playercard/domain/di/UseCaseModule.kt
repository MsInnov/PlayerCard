package com.mscode.playercard.domain.di

import com.mscode.playercard.domain.usecase.GetLeaguesUseCase
import com.mscode.playercard.domain.usecase.GetTeamsUseCase
import com.mscode.playercard.domain.usecase.GetPlayersUseCase
import com.mscode.playercard.domain.usecase.GetPlayerFavoriteUseCase
import com.mscode.playercard.domain.usecase.GetPlayersFavoriteUseCase
import com.mscode.playercard.domain.usecase.RemovePlayerFavoriteUseCase
import com.mscode.playercard.domain.usecase.SavePlayerFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetLeaguesUseCase)
    factoryOf(::GetTeamsUseCase)
    factoryOf(::GetPlayersUseCase)
    factoryOf(::GetPlayerFavoriteUseCase)
    factoryOf(::GetPlayersFavoriteUseCase)
    factoryOf(::RemovePlayerFavoriteUseCase)
    factoryOf(::SavePlayerFavoriteUseCase)
}