package com.example.banchan.di

import com.example.banchan.data.source.remote.BanChanDataSource
import com.example.banchan.data.source.remote.BanChanRemoteDataSource
import com.example.banchan.data.api.BanChanApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideBanChanRemoteDataSource(
        banChanApi : BanChanApi,
        ioDispatcher: CoroutineDispatcher
    ) : BanChanDataSource {
        return BanChanRemoteDataSource(banChanApi, ioDispatcher)
    }
}