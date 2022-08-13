package com.example.banchan.di

import com.example.banchan.data.repository.BanChanRepository
import com.example.banchan.data.repository.BanChanRepositoryImpl
import com.example.banchan.data.repository.BasketRepository
import com.example.banchan.data.repository.BasketRepositoryImpl
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
}