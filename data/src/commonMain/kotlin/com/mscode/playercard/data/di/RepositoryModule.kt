package com.mscode.playercard.data.di

import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.data.repository.LeaguesDataRepository
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.data.repository.TeamsDataRepository
import com.mscode.playercard.domain.repository.PlayersRepository
import com.mscode.playercard.data.repository.PlayersDataRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LeaguesDataRepository) { bind<LeaguesRepository>() }
    singleOf(::TeamsDataRepository) { bind<TeamsRepository>() }
    singleOf(::PlayersDataRepository) { bind<PlayersRepository>() }
}