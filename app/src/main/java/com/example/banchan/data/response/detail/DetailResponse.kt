package com.example.banchan.data.response.detail

import com.example.banchan.domain.model.BasketModel
import com.example.banchan.domain.model.ProductDetailModel
import com.example.banchan.domain.model.RecentlyProductItemModel
import com.example.banchan.domain.model.RecentlyProductModel
import com.example.banchan.util.calcul.calculationPercent
import com.example.banchan.util.ext.toNum
import com.example.banchan.util.ext.toTimeFormat
import kotlinx.serialization.Serializable
import java.util.*

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
            discountPercent = if (data.prices.size == 1) null else calculationPercent(
                data.prices[0],
                data.prices[1]
            ),
            description = data.productDescription,
            thumbImages = data.thumbImages,
            detailSection = data.detailSection,
            deliveryInfo = data.deliveryInfo,
            deliveryFee = data.deliveryFee
        )
    }

    fun toBasketModel(name: String, count: Int, isSelected: Boolean): BasketModel {
        return BasketModel(
            name = name,
            count = count,
            isChecked = isSelected,
            detailHash = hash,
            price = if (data.prices.size == 1) data.prices[0].toNum() else data.prices[1].toNum(),
            image = data.thumbImages[0],
        )
    }

    fun toRecentlyProductModel(name: String, time: String): RecentlyProductItemModel {
        return RecentlyProductItemModel(
            detailHash = hash,
            image = data.thumbImages[0],
            name = name,
            viewedTime = time,
            originPrice = data.prices[0].toNum(),
            discountPrice = if (data.prices.size == 1) null else data.prices[1].toNum(),
        )
    }

    fun toRecentlyModel(date: Date, name: String, isCartAdded: Boolean): RecentlyProductModel {
        return RecentlyProductModel(
            title = name,
            detailHash = hash,
            originPrice = data.prices[0],
            discountPrice = if (data.prices.size == 1) null else data.prices[1],
            discountPercent = if (data.prices.size == 1) null else "${
                calculationPercent(
                    data.prices[0],
                    data.prices[1]
                )
            }%",
            time = ((Date().time - date.time) / 1000).toTimeFormat(),
            description = data.productDescription,
            image = data.thumbImages[0],
            isCartAdded = isCartAdded
        )
    }
}