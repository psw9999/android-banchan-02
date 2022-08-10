package com.example.banchan.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutinesModule {

    @Provides
    @Singleton
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}