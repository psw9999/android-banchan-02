package com.example.banchan.di

import com.example.banchan.data.repository.BanChanRepositoryImpl
import com.example.banchan.data.source.remote.BanChanRemoteDataSource
import com.example.banchan.data.repository.BanChanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideBanChanRepository(
        banChanDataSource: BanChanRemoteDataSource
    ): BanChanRepository {
        return BanChanRepositoryImpl(banChanDataSource)
    }
}