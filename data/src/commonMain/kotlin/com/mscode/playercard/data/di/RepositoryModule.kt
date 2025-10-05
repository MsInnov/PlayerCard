package com.mscode.playercard.data.di

import com.mscode.playercard.domain.repository.LeaguesRepository
import com.mscode.playercard.data.repository.LeaguesDataRepository
import com.mscode.playercard.domain.repository.TeamsRepository
import com.mscode.playercard.data.repository.TeamsDataRepository
import com.mscode.playercard.domain.repository.PlayersRepository
import com.mscode.playercard.data.repository.PlayersDataRepository
import com.mscode.playercard.data.localDataSource.localVolatile.LeaguesLocalData
import com.mscode.playercard.data.localDataSource.localVolatile.PlayersLocalData
import com.mscode.playercard.data.localDataSource.localVolatile.TeamsLocalData
import com.mscode.playercard.data.localDataSource.localPersistent.FavoritesPlayersData
import com.mscode.playercard.data.localDataSource.localPersistent.KeyValueStorage
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
    singleOf(::FavoritesPlayersData)
    singleOf(::LeaguesLocalData)
    singleOf(::PlayersLocalData)
    singleOf(::TeamsLocalData)
    single<KeyValueStorage> { get() }
}