package com.example.banchan.di

import com.example.banchan.data.source.BanChanDataSource
import com.example.banchan.data.source.BasketDataSource
import com.example.banchan.data.source.local.BasketLocalDataSource
import com.example.banchan.data.source.remote.BanChanRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindBanChanRemoteDataSource(
        banChanRemoteDataSource: BanChanRemoteDataSource
    ): BanChanDataSource

    @Singleton
    @Binds
    abstract fun bindBasketLocalDataSource(
        basketLocalDataSource: BasketLocalDataSource
    ): BasketDataSource
}

