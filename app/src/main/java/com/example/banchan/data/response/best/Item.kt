package com.example.banchan.data.response.best

import com.example.banchan.domain.model.ItemModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val alt: String,
    val badge: List<String>? = null,
    @SerialName("delivery_type")
    val deliveryType: List<String>,
    val description: String,
    @SerialName("detail_hash")
    val detailHash: String,
    val image: String,
    @SerialName("n_price")
    val nPrice: String? = null,
    @SerialName("s_price")
    val sPrice: String,
    val title: String
) {
    fun toItemModel() : ItemModel {
        return ItemModel(
            title = title,
            description = description,
            detailHash = detailHash,
            image = image,
            originPrice = nPrice ?: sPrice,
            discountPrice = if(nPrice!=null) sPrice else null,
            discountPercent = if(nPrice!=null) "${calculationPercent(nPrice, sPrice)}%" else null
        )
    }

    private fun calculationPercent(originPriceStr : String, discountPriceStr : String) : Int {
        val numberRegex = Regex("[^0-9]")
        val originPrice = originPriceStr.replace(numberRegex, "").toDouble()
        val discountPrice = discountPriceStr.replace(numberRegex, "").toDouble()
        return (((originPrice-discountPrice) / originPrice) * 100.0).toInt()
    }

}