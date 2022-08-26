package com.example.banchan.data.response.side

import com.example.banchan.data.response.best.Item
import com.example.banchan.domain.model.ItemModel
import kotlinx.serialization.Serializable

@Serializable
data class SideResponse(
    val body: List<Item>,
    val statusCode: Int
) {
    fun toSoupModel(): List<ItemModel> {
        return body.map { it.toItemModel() }
    }
}