package com.example.banchan.data.response.detail

import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.util.calcul.calculationPercent
import com.example.banchan.util.ext.toNum
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
    val data: Data,
    val hash: String
) {
    fun toDetailModel(name: String): ProductDetailModel {
        return ProductDetailModel(
            name = name,
            detailHash = hash,
            point = data.point.toNum(),
            originPrice = data.prices[0].toNum(),
            discountPrice = if (data.prices.size == 1) null else data.prices[1].toNum(),
            discountPercent = if (data.prices.size == 1) null else calculationPercent(data.prices[0],data.prices[1]),
            description = data.productDescription,
            thumbImages = data.thumbImages,
            detailSection = data.detailSection,
            deliveryInfo = data.deliveryInfo,
            deliveryFee = data.deliveryFee
        )
    }
}