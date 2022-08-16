package com.example.banchan.data.source.local.recent

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyProductDao {
    @Query("SELECT * FROM recentlyproduct")
    fun getRecentlyProducts(): Flow<List<RecentlyProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentlyProduct(vararg recentlyProduct: RecentlyProduct)

    @Update
    fun updateRecentlyProduct(vararg recentlyProduct: RecentlyProduct)
}