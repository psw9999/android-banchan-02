package com.example.banchan.domain.model

import com.example.banchan.presentation.home.maincook.Filter
import com.example.banchan.presentation.adapter.main.Type

data class ItemModel(
    val description: String,
    val detailHash: String,
    val image: String,
    val discountPercent: String?,
    val discountPrice: String? = null,
    val originPrice: String,
    val title: String,
    val isCartAdded: Boolean = false
)

sealed class ItemListModel {
    data class Header(val currentType: Type, val currentFilter: Filter) : ItemListModel()
    data class SmallItem(val item: ItemModel) : ItemListModel()
    data class LargeItem(val item: ItemModel) : ItemListModel()
}