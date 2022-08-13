package com.example.banchan.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BasketItem::class], version = 1, exportSchema = false)
abstract class BanChanDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
}