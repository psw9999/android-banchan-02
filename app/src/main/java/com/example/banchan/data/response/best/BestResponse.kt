package com.example.banchan.data.response.best

import kotlinx.serialization.Serializable

@Serializable
data class BestResponse(
    val body: List<Body>,
    val statusCode: Int
)