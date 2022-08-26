package com.example.banchan.data.response.soup

import com.example.banchan.data.response.best.Item
import com.example.banchan.domain.model.ItemModel
import kotlinx.serialization.Serializable

@Serializable
data class SoupResponse(
    val body: List<Item>,
    val statusCode: Int
){
    fun toSoupModel(): List<ItemModel> {
        return body.map { it.toItemModel() }
    }
}