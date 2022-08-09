package com.example.banchan.data.response.side

import com.example.banchan.data.response.best.Item
import kotlinx.serialization.Serializable

@Serializable
data class SideResponse(
    val body: List<Item>,
    val statusCode: Int
)