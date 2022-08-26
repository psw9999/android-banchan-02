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

    @Query("UPDATE basketitem SET isSelected = :isSelected")
    fun updateAllBasketIsSelected(isSelected: Int)

    @Delete
    fun deleteBasketItem(vararg basketItem: BasketItem)

    @Query("DELETE FROM basketitem WHERE isSelected = 1")
    fun deleteSelectedBasketItem()

}