package com.example.banchan.data.response.soup

import com.example.banchan.data.response.best.Item
import kotlinx.serialization.Serializable

@Serializable
data class SoupResponse(
    val body: List<Item>,
    val statusCode: Int
)