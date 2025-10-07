package com.mscode.playercard.data.di

import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.data.repository.LeaguesDataRepository
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.data.repository.TeamsDataRepository
import com.mscode.playercard.domain.repository.PlayersRepository
import com.mscode.playercard.data.repository.PlayersDataRepository
import com.mscode.playercard.data.localdatasource.localvolatile.LeaguesLocalData
import com.mscode.playercard.data.localdatasource.localvolatile.PlayersLocalData
import com.mscode.playercard.data.localdatasource.localvolatile.TeamsLocalData
import com.mscode.playercard.data.localdatasource.localpersistent.FavoritesPlayersData
import com.mscode.playercard.data.localdatasource.localpersistent.KeyValueStorage
import com.mscode.playercard.data.localsource.FavoritesPlayersLocalDataSource
import com.mscode.playercard.data.localsource.LeaguesLocalDataSource
import com.mscode.playercard.data.localsource.PlayersLocalDataSource
import com.mscode.playercard.data.localsource.TeamsLocalDataSource
import com.mscode.playercard.data.repository.PlayersFavoriteDataRepository
import com.mscode.playercard.domain.repository.PlayersFavoriteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LeaguesDataRepository) { bind<LeaguesRepository>() }
    singleOf(::TeamsDataRepository) { bind<TeamsRepository>() }
    singleOf(::PlayersDataRepository) { bind<PlayersRepository>() }
    singleOf(::PlayersFavoriteDataRepository) { bind<PlayersFavoriteRepository>() }
    singleOf(::FavoritesPlayersData) { bind<FavoritesPlayersLocalDataSource>() }
    singleOf(::LeaguesLocalData) { bind<LeaguesLocalDataSource>() }
    singleOf(::PlayersLocalData) { bind<PlayersLocalDataSource>() }
    singleOf(::TeamsLocalData) { bind<TeamsLocalDataSource>() }
    single<KeyValueStorage> { get() }
}