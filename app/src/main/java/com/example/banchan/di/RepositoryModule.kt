package com.example.banchan.di

import com.example.banchan.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBanChanRepository(
        banChanRepositoryImpl: BanChanRepositoryImpl
    ): BanChanRepository

    @Singleton
    @Binds
    abstract fun bindBasketRepository(
        basketRepositoryImpl: BasketRepositoryImpl
    ): BasketRepository

    @Singleton
    @Binds
    abstract fun bindRecentlyProductRepository(
        recentlyProductRepositoryImpl: RecentlyProductRepositoryImpl
    ): RecentlyProductRepository

    @Singleton
    @Binds
    abstract fun bindHistoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository
}