package com.example.banchan.data.source.local.basket

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {
    @Query("SELECT * FROM basketitem")
    fun getBasketItems(): Flow<List<BasketItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBasketItem(vararg basketItem: BasketItem)

    @Update
    fun updateBasketItem(vararg basketItem: BasketItem)
}