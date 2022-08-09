package com.example.banchan.data.response.detail

import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
    val data: Data,
    val hash: String
)