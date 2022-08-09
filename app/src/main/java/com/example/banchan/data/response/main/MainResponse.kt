package com.example.banchan.data.response.main

import com.example.banchan.data.response.best.Item
import kotlinx.serialization.Serializable

@Serializable
data class MainResponse(
    val body: List<Item>,
    val statusCode: Int
)