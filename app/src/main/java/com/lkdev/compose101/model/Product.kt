package com.lkdev.compose101.model

import com.squareup.moshi.Json
import java.math.RoundingMode
import java.text.DecimalFormat

data class Product(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "image") val image: String,
    @Json(name = "content") val content: String,
    @Json(name = "isNewProduct") val isNewProduct: Boolean,
    @Json(name = "price") val price: Double,
) {
    fun getPriceFormat(): String {
        return DecimalFormat("#.##").apply {
            roundingMode = RoundingMode.FLOOR
        }.format(price)
    }
}
