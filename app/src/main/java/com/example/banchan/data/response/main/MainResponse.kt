package com.example.banchan.data.response.main

import com.example.banchan.data.response.best.Item
import com.example.banchan.domain.model.BestModel
import com.example.banchan.domain.model.ItemModel
import kotlinx.serialization.Serializable

@Serializable
data class MainResponse(
    val body: List<Item>,
    val statusCode: Int
) {
    fun toMainModel(): List<ItemModel> {
        return body.map { it.toItemModel() }
    }
}