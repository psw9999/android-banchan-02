package com.example.banchan.data.response.best

import com.example.banchan.domain.model.BestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Body(
    @SerialName("category_id")
    val categoryId: String,
    val items: List<Item>,
    val name: String
) {
    fun toBestModel() : BestModel {
        return BestModel(
            title = name,
            items = items.map{
                it.toItemModel()
            }
        )
    }
}