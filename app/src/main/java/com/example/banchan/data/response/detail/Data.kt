package com.example.banchan.data.response.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("delivery_fee")
    val deliveryFee: String,
    @SerialName("delivery_info")
    val deliveryInfo: String,
    @SerialName("detail_section")
    val detailSection: List<String>,
    val point: String,
    val prices: List<String>,
    @SerialName("product_description")
    val productDescription: String,
    @SerialName("thumb_images")
    val thumbImages: List<String>,
    @SerialName("top_image")
    val topImage: String
)