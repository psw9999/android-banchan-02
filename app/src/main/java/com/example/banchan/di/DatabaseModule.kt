package com.example.banchan.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.banchan.data.source.local.BanChanDatabase
import com.example.banchan.data.source.local.BasketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideBanChanDatabase(@ApplicationContext context: Context): BanChanDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BanChanDatabase::class.java,
            "banchan.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBasketDao(database: BanChanDatabase): BasketDao = database.basketDao()
}