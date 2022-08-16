package com.example.banchan.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.banchan.data.Converter
import com.example.banchan.data.source.local.basket.BasketDao
import com.example.banchan.data.source.local.basket.BasketItem
import com.example.banchan.data.source.local.recent.RecentlyProduct
import com.example.banchan.data.source.local.recent.RecentlyProductDao

@Database(entities = [BasketItem::class, RecentlyProduct::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class BanChanDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
    abstract fun recentlyProductDao(): RecentlyProductDao
}