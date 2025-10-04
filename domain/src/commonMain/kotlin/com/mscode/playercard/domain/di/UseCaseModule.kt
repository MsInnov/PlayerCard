package com.mscode.playercard.domain.di

import com.mscode.playercard.domain.usecase.GetLeaguesUseCase
import com.mscode.playercard.domain.usecase.GetTeamsUseCase
import com.mscode.playercard.domain.usecase.GetPlayersUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetLeaguesUseCase)
    factoryOf(::GetTeamsUseCase)
    factoryOf(::GetPlayersUseCase)
}